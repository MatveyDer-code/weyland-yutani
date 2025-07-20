package audit.config;

import audit.AuditMode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "audit")
public class AuditProperties {
    private AuditMode mode = AuditMode.CONSOLE;
    private String kafkaTopic;
}