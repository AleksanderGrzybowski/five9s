package pl.kelog.five9s.yamlimport;

import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

@Service
@Log
public class ServiceDefinitionRepository {
    
    private final List<ServiceDefinition> definitions;
    
    public ServiceDefinitionRepository(
            @Value("${app.configPath}") String configPath,
            Parser parser
    ) throws IOException {
        log.info("Loading configuration from " + configPath + "...");
        
        File definitionsFile = new File(configPath);
        if (!definitionsFile.exists()) {
            throw new DefinitionFileNotFoundException("Config file " + configPath + " not found");
        }
        String yamlContent = FileUtils.readFileToString(definitionsFile, Charset.defaultCharset());
        definitions = Collections.unmodifiableList(parser.parse(yamlContent));
        
        log.info("Configuration loaded, " + definitions.size() + " definitions.");
    }
    
    public List<ServiceDefinition> findAll() {
        return definitions;
    }
    
    private class DefinitionFileNotFoundException extends RuntimeException {
        DefinitionFileNotFoundException(String message) {
            super(message);
        }
    }
}
