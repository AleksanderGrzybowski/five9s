package pl.kelog.five9s.checks;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static pl.kelog.five9s.checks.CheckStatus.Status.DOWN;
import static pl.kelog.five9s.checks.CheckStatus.Status.UP;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CheckStatus {
    public final Status status;
    public final String log;
    
    static CheckStatus up(String log) {
        return new CheckStatus(UP, log);
    }
    
    static CheckStatus down(String log) {
        return new CheckStatus(DOWN, log);
    }
    
    public enum Status {
        UP, DOWN
    }
}
