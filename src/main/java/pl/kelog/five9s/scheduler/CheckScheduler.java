package pl.kelog.five9s.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.kelog.five9s.checks.CheckStatus;
import pl.kelog.five9s.checks.HttpChecker;
import pl.kelog.five9s.checks.SshChecker;
import pl.kelog.five9s.db.PerformedCheckInfo;
import pl.kelog.five9s.db.PerformedCheckRepository;
import pl.kelog.five9s.utils.DateService;
import pl.kelog.five9s.yamlimport.ServiceDefinition;
import pl.kelog.five9s.yamlimport.ServiceDefinition.CheckDefinition;
import pl.kelog.five9s.yamlimport.ServiceDefinitionRepository;

@Service
@RequiredArgsConstructor
@Log
public class CheckScheduler {
    
    private final ServiceDefinitionRepository serviceDefinitionRepository;
    private final PerformedCheckRepository performedCheckRepository;
    private final HttpChecker httpChecker;
    private final SshChecker sshChecker;
    private final DateService dateService;
    
    @Scheduled(fixedRateString = "${app.checkInterval}", initialDelay = 10000)
    public void loop() {
        log.info("Starting check loop...");
        
        serviceDefinitionRepository.findAll().forEach(serviceDefinition -> performCheck(serviceDefinition));
        
        log.info("Check loop finished.");
    }
    
    private void performCheck(ServiceDefinition serviceDefinition) {
        log.info("Running " + serviceDefinition.name + "(" + serviceDefinition.description + ")...");
        CheckDefinition checkDefinition = serviceDefinition.checkDefinition;
        
        CheckStatus status;
        if (checkDefinition.type == ServiceDefinition.CheckType.HTTP) {
            status = httpChecker.perform(checkDefinition.url, 10000);
            
        } else if (checkDefinition.type == ServiceDefinition.CheckType.SSH) {
            status = sshChecker.perform(
                    checkDefinition.host,
                    checkDefinition.user,
                    checkDefinition.keyPath,
                    checkDefinition.command
            );
        } else {
            throw new AssertionError("Unknown check type " + checkDefinition.type);
        }
        
        log.info("Status of " + serviceDefinition.name + " -> " + status + ", saving.");
        PerformedCheckInfo info = new PerformedCheckInfo();
        info.setName(serviceDefinition.name);
        info.setTimestamp(dateService.now());
        info.setStatus(status.status);
        info.setLog(status.log);
        performedCheckRepository.save(info);
    }
}
