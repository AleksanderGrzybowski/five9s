package pl.kelog.five9s.yamlimport;

import org.yaml.snakeyaml.Yaml;
import pl.kelog.five9s.yamlimport.ServiceDefinition.CheckDefinition;
import pl.kelog.five9s.yamlimport.ServiceDefinition.CheckType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * I could not find a way to use POJO mappings, so I rolled it out by hand.
 */
class Parser {
    
    private static final Yaml YAML = new Yaml();
    
    List<ServiceDefinition> parse(String yamlContent) {
        return iteratorToList(YAML.loadAll(yamlContent)).stream()
                .map(Parser::createDefinition)
                .collect(toList());
    }
    
    @SuppressWarnings("unchecked")
    private static ServiceDefinition createDefinition(Object serviceDefinition) {
        LinkedHashMap<String, Object> definitionMap = (LinkedHashMap<String, Object>) serviceDefinition;
        LinkedHashMap<String, Object> checkMap = (LinkedHashMap<String, Object>) definitionMap.get("check");
        CheckType checkType = CheckType.valueOf(((String) checkMap.get("type")).toUpperCase());
        
        CheckDefinition checkDefinition;
        if (checkType == CheckType.HTTP) {
            checkDefinition = CheckDefinition.http(
                    (String) checkMap.get("host"), (String) checkMap.get("url")
            );
        } else if (checkType == CheckType.SSH) {
            checkDefinition = CheckDefinition.ssh(
                    (String) checkMap.get("host"), (String) checkMap.get("keyPath"),
                    (String) checkMap.get("user"), (String) checkMap.get("command")
            );
        } else {
            throw new AssertionError("Unknown check type");
        }
        
        return new ServiceDefinition(
                (String) definitionMap.get("name"),
                (String) definitionMap.get("description"),
                checkDefinition
        );
    }
    
    private static <T> List<T> iteratorToList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.iterator().forEachRemaining(list::add);
        return list;
    }
}
