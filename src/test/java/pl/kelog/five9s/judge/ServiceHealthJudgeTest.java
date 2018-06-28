package pl.kelog.five9s.judge;

import org.junit.Before;
import org.junit.Test;
import pl.kelog.five9s.checks.CheckStatus;
import pl.kelog.five9s.db.PerformedCheckInfo;
import pl.kelog.five9s.db.PerformedCheckRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.kelog.five9s.judge.ServiceHealthJudge.WARN_THRESHOLD;
import static pl.kelog.five9s.judge.ServiceStatus.*;

public class ServiceHealthJudgeTest {
    
    private static final String SERVICE_NAME = "google.com";
    
    private ServiceHealthJudge judge;
    
    private PerformedCheckRepository performedCheckRepository;
    
    @Before
    public void setup() {
        performedCheckRepository = mock(PerformedCheckRepository.class);
        judge = new ServiceHealthJudge(performedCheckRepository);
    }
    
    @Test
    public void should_report_UNKNOWN_if_there_are_no_performed_checks_at_all() {
        mockRepository(emptyList());
        
        assertThat(judge.statusOf(SERVICE_NAME)).isEqualTo(UNKNOWN);
    }
    
    @Test
    public void should_report_UP_if_the_last_check_was_ok() {
        mockRepository(createCheckList(true));
        
        assertThat(judge.statusOf(SERVICE_NAME)).isEqualTo(UP);
    }
    
    @Test
    public void should_report_WARN_if_the_last_1_to_THRESHOLD_checks_were_error() {
        for (int i = 1; i < WARN_THRESHOLD; ++i) {
            mockRepository(createDownCheckList(i));
            
            assertThat(judge.statusOf(SERVICE_NAME)).isEqualTo(WARN);
        }
    }
    
    @Test
    public void should_report_DOWN_if_THRESHOLD_or_more_of_the_last_checks_were_error() {
        for (int i = WARN_THRESHOLD; i <= WARN_THRESHOLD * 2; ++i) {
            mockRepository(createDownCheckList(i));
            
            assertThat(judge.statusOf(SERVICE_NAME)).isEqualTo(DOWN);
        }
    }
    
    private void mockRepository(List<PerformedCheckInfo> objects) {
        when(performedCheckRepository.findAll(SERVICE_NAME)).thenReturn(objects);
    }
    
    private static List<PerformedCheckInfo> createDownCheckList(int count) {
        boolean[] checks = new boolean[count]; // default false
        return createCheckList(checks);
    }
    
    private static List<PerformedCheckInfo> createCheckList(boolean... values) {
        List<PerformedCheckInfo> list = new ArrayList<>();
        
        for (boolean value : values) {
            PerformedCheckInfo info = new PerformedCheckInfo();
            info.setStatus(value ? CheckStatus.UP : CheckStatus.DOWN);
            list.add(info);
        }
        
        return list;
    }
}