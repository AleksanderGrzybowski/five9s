package pl.kelog.five9s.checks;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pl.kelog.five9s.checks.CheckStatus.Status.DOWN;
import static pl.kelog.five9s.checks.CheckStatus.Status.UP;


@Ignore
public class SshCheckerTest {
    
    // Use only when needed
    private static final String HOSTNAME = "kelog.pl";
    private static final String USERNAME = "icinga";
    private static final String KEY_PATH = "/tmp/kelogpl_id_rsa";
    private static final String CHECK_DISK_COMMAND = "check_disk.sh";
    
    private SshChecker sshChecker;
    
    
    @Before
    public void setup() {
        sshChecker = new SshChecker();
    }
    
    @Test
    public void should_return_UP_if_remote_command_returns_exit_code_SUCCESS() {
        assertThat(
                sshChecker.perform(HOSTNAME, USERNAME, KEY_PATH, CHECK_DISK_COMMAND).status
        ).isEqualTo(UP);
    }
    
    @Test
    public void should_return_DOWN_if_remote_command_returns_exit_code_ERROR() {
        assertThat(
                sshChecker.perform(HOSTNAME, USERNAME, KEY_PATH, "false").status
        ).isEqualTo(DOWN);
    }
    
    @Test
    public void should_return_DOWN_if_remote_host_ssh_server_rejects_authentication() {
        assertThat(
                sshChecker.perform(HOSTNAME, "nonexistentuser", KEY_PATH, CHECK_DISK_COMMAND).status
        ).isEqualTo(DOWN);
    }
    
    @Test
    public void should_return_DOWN_if_remote_host_has_no_ssh_server_running() {
        assertThat(
                sshChecker.perform("google.com", USERNAME, KEY_PATH, CHECK_DISK_COMMAND).status
        ).isEqualTo(DOWN);
    }
    
    @Test
    public void should_return_DOWN_if_remote_hostname_cant_be_resolved() {
        assertThat(
                sshChecker.perform("kelog2.pl", USERNAME, KEY_PATH, CHECK_DISK_COMMAND).status
        ).isEqualTo(DOWN);
    }
}