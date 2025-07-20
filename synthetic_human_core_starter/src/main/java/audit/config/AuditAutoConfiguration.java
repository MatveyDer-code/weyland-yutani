package audit.config;

import audit.*;
import audit.impl.ConsoleAuditSender;
import audit.impl.KafkaAuditSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AuditProperties.class)
public class AuditAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditSender auditSender(AuditProperties properties) {
        return switch (properties.getMode()) {
            case CONSOLE -> new ConsoleAuditSender();
            case KAFKA -> new KafkaAuditSender(); // заменить на реальный
        };
    }
}