package pl.kelog.five9s.checks;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static pl.kelog.five9s.checks.CheckStatus.down;
import static pl.kelog.five9s.checks.CheckStatus.up;

@Service
@RequiredArgsConstructor
public class HttpChecker {
    
    private final RestTemplateBuilder restTemplateBuilder;
    
    public CheckStatus perform(String url, int timeout) {
        RestTemplate restTemplate = withTimeout(timeout);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, HttpEntity.EMPTY, String.class
            );
            
            return up("Status code: " + response.getStatusCode());
        } catch (HttpServerErrorException e) {
            return down("Status code: " + e.getStatusCode());
        } catch (ResourceAccessException e) {
            return down("Other error: " + e.getMessage());
        } catch (Exception e) {
            return down("Unexpected error: " + e.getMessage());
        }
    }
    
    private RestTemplate withTimeout(int timeout) {
        return restTemplateBuilder
                .setConnectTimeout(timeout)
                .setReadTimeout(timeout)
                .build();
    }
}
