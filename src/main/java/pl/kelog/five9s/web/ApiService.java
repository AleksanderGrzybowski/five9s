package pl.kelog.five9s.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kelog.five9s.judge.ServiceHealthJudge;
import pl.kelog.five9s.judge.ServiceStatus;
import pl.kelog.five9s.yamlimport.ServiceDefinitionRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
class ApiService {
    
    private final ServiceHealthJudge judge;
    private final ServiceDefinitionRepository serviceDefinitionRepository;
    
    List<ServiceDto> getAll() {
        return serviceDefinitionRepository.findAll().stream().map(definition ->
                new ServiceDto(definition.name, definition.description, judge.statusOf(definition.name))
        ).collect(toList());
    }

    @RequiredArgsConstructor
    public static class ServiceDto {
        public final String name;
        public final String description;
        public final ServiceStatus status;
    }
}
