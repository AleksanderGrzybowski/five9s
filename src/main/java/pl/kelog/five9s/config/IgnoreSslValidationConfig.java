package pl.kelog.five9s.config;

import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * http://forum.spring.io/forum/spring-projects/web-services/52629-disabling-hostname-verification
 */
@Configuration
public class IgnoreSslValidationConfig {
    
    public IgnoreSslValidationConfig() throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            
            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
            
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{trustManager}, null);
        SSLContext.setDefault(ctx);
    }
}
