package pl.kelog.five9s.checks;

import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import static java.text.MessageFormat.format;
import static pl.kelog.five9s.checks.CheckStatus.down;
import static pl.kelog.five9s.checks.CheckStatus.up;

@Service
@Slf4j
public class SshChecker {
    
    private static final int TIMEOUT = 2000;
    private static final int EXIT_SUCCESS_STATUS = 0;
    
    public CheckStatus perform(String host, String username, String sshKeyPath, String command) {
        try {
            return performInternal(host, username, sshKeyPath, command);
        } catch (IOException e) {
            log.info("Exception thrown when performing SSH check", e);
            return down("Exception: " + e.getMessage());
        }
    }
    
    private CheckStatus performInternal(String host, String username, String sshKeyPath, String command) throws IOException {
        log.info(format(
                "Performing SSH check, host {0}, username {1}, sshKeyPath {2}, command '{3}'...",
                host, username, sshKeyPath, command
        ));
        
        SSHClient client = new SSHClient();
        client.setConnectTimeout(TIMEOUT);
        skipHostKeyVerification(client);
        
        client.connect(host);
        client.authPublickey(username, sshKeyPath);
        Session session = client.startSession();
        
        Command cmd = session.exec(command);
        cmd.join(TIMEOUT, TimeUnit.MILLISECONDS);
        String commandOutput = IOUtils.toString(cmd.getInputStream(), Charset.defaultCharset());
        Integer exitStatus = cmd.getExitStatus();
        
        String message = "Command {0} returned exit code {1}, output: {2}";
        log.info(format(message,
                command, exitStatus, commandOutput
        ));
        
        return exitStatus == EXIT_SUCCESS_STATUS ? up(message) : down(message);
    }
    
    private void skipHostKeyVerification(SSHClient client) {
        client.addHostKeyVerifier((hostname, port, key) -> true);
    }
}
