package pl.kelog.five9s.checks;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static pl.kelog.five9s.checks.CheckStatus.DOWN;
import static pl.kelog.five9s.checks.CheckStatus.UP;

@Service
@RequiredArgsConstructor
public class HttpChecker {
    
    private final RestTemplateBuilder restTemplateBuilder;
    
    CheckStatus perform(String url, int timeout) {
        HttpStatus httpStatus = makeRequest(url, timeout);
        return httpStatus.isError() ? DOWN : UP;
    }
    
    private HttpStatus makeRequest(String url, int timeout) {
        RestTemplate restTemplate = withTimeout(timeout);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, HttpEntity.EMPTY, String.class
            );
            
            return response.getStatusCode();
        } catch (HttpServerErrorException e) {
            return e.getStatusCode();
        } catch (ResourceAccessException e) {
            return HttpStatus.GATEWAY_TIMEOUT; // effectively
        }
    }
    
    private RestTemplate withTimeout(int timeout) {
        return restTemplateBuilder
                .setConnectTimeout(timeout)
                .setReadTimeout(timeout)
                .build();
    }
}
