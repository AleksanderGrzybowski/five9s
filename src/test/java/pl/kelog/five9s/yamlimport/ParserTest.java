package pl.kelog.five9s.yamlimport;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.kelog.five9s.yamlimport.ServiceDefinition.CheckDefinition.http;
import static pl.kelog.five9s.yamlimport.ServiceDefinition.CheckDefinition.ssh;

public class ParserTest {
    
    private Parser parser = new Parser();
    
    @Test
    public void should_parse_sample_file() throws Exception {
        List<ServiceDefinition> definitions = parser.parse(readResource("/sample.yml"));
        
        assertThat(definitions).hasSize(2);
        
        ServiceDefinition http = definitions.get(0);
        assertThat(http).isEqualTo(new ServiceDefinition(
                "aleksandergrzybowski.pl",
                "My website",
                http("http://aleksandergrzybowski.pl/index.html")
        ));
        
        ServiceDefinition sshDef = definitions.get(1);
        assertThat(sshDef).isEqualTo(new ServiceDefinition(
                "kelog.pl",
                "My server",
                ssh("kelog.pl", "/id_rsa", "test", "check_disk.sh")
        ));
    }
    
    @SuppressWarnings("SameParameterValue")
    private String readResource(String filename) throws IOException {
        return IOUtils.toString(IOUtils.toByteArray(getClass().getResourceAsStream(filename)), "UTF-8");
    }
}