package pl.kelog.five9s.judge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kelog.five9s.checks.CheckStatus;
import pl.kelog.five9s.db.PerformedCheckInfo;
import pl.kelog.five9s.db.PerformedCheckRepository;

import java.util.List;

import static pl.kelog.five9s.judge.ServiceStatus.*;

@Service
@RequiredArgsConstructor
public class ServiceHealthJudge {
    
    static final int WARN_THRESHOLD = 5;
    
    private final PerformedCheckRepository performedCheckRepository;
    
    public ServiceStatus statusOf(String serviceName) {
        List<PerformedCheckInfo> checks = performedCheckRepository.findAll(serviceName);
        
        if (checks.isEmpty()) {
            return unknown();
        }
        
        if (checks.get(checks.size() - 1).getStatus() == CheckStatus.Status.UP) {
            return up();
        }
        
        int downCounter = 0;
        for (int i = checks.size() - 1; i >= 0; i--) {
            if (checks.get(i).getStatus() == CheckStatus.Status.DOWN) {
                downCounter++;
            } else {
                break;
            }
        }
        
        return downCounter >= WARN_THRESHOLD ? down(downCounter) : warn(downCounter);
    }
}
