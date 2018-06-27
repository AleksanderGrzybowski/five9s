package pl.kelog.five9s;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Five9sApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(Five9sApplication.class, args);
    }
}
