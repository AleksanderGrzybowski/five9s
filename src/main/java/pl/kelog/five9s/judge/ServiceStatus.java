package pl.kelog.five9s.judge;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ServiceStatus {
    
    public final Status status;
    public final int failureCount;
    
    static ServiceStatus up() {
        return new ServiceStatus(Status.UP, 0);
    }
    
    static ServiceStatus unknown() {
        return new ServiceStatus(Status.UNKNOWN, 0);
    }
    
    static ServiceStatus warn(int failureCount) {
        return new ServiceStatus(Status.WARN, failureCount);
    }
    
    static ServiceStatus down(int failureCount) {
        return new ServiceStatus(Status.DOWN, failureCount);
    }
    
    public enum Status {
        UNKNOWN, DOWN, WARN, UP
    }
}
