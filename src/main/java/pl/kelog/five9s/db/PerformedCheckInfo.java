package pl.kelog.five9s.db;

import lombok.Data;
import pl.kelog.five9s.checks.CheckStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class PerformedCheckInfo {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    private String name;
    
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    private CheckStatus.Status status;
    
    @Lob
    private String log = "";
}
