package pl.kelog.five9s.checks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.kelog.five9s.checks.CheckStatus.Status.DOWN;
import static pl.kelog.five9s.checks.CheckStatus.Status.UP;

@SpringBootTest
@RunWith(SpringRunner.class)

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        QuartzAutoConfiguration.class
})
public class HttpCheckerTest {
    
    @Autowired
    private HttpChecker checker;
    
    private static final String MOCK_SERVER = "http://httpstat.us";
    private static final int DEFAULT_TIMEOUT = 10000;
    
    @Test
    public void should_return_UP_on_http_200() {
        assertThat(
                checker.perform(MOCK_SERVER + "/200", DEFAULT_TIMEOUT).status
        ).isEqualTo(UP);
    }
    
    @Test
    public void should_return_DOWN_on_http_500() {
        assertThat(
                checker.perform(MOCK_SERVER + "/500", DEFAULT_TIMEOUT).status
        ).isEqualTo(DOWN);
    }
    
    @Test
    public void should_return_DOWN_on_timeout() {
        assertThat(
                checker.perform(MOCK_SERVER + "/500?sleep=" + DEFAULT_TIMEOUT / 10, DEFAULT_TIMEOUT).status
        ).isEqualTo(DOWN);
    }
}