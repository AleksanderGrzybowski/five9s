package pl.kelog.five9s.yamlimport;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ServiceDefinition {
    public final String name;
    public final String description;
    
    public final CheckDefinition checkDefinition;
    
    @EqualsAndHashCode
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    @ToString
    static class CheckDefinition {
        
        static CheckDefinition http(String url) {
            return new CheckDefinition(CheckType.HTTP, url, null, null, null, null);
        }
        
        static CheckDefinition ssh(String host, String keyPath, String user, String command) {
            return new CheckDefinition(CheckType.SSH, null, host, keyPath, user, command);
        }
        
        public final CheckType type;
        
        // for HTTP
        public final String url;
        
        // for SSH
        public final String host;
        public final String keyPath;
        public final String user;
        public final String command;
    }
    
    enum CheckType {
        HTTP, SSH
    }
}
