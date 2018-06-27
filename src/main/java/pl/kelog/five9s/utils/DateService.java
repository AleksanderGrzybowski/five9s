package pl.kelog.five9s.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateService {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
