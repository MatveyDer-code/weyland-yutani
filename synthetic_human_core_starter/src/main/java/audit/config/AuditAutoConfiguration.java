package audit.config;

import audit.*;
import audit.impl.ConsoleAuditSender;
import audit.impl.KafkaAuditSender;
import audit.model.AuditEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableConfigurationProperties(AuditProperties.class)
public class AuditAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditSender auditSender(AuditProperties properties,
                                   KafkaTemplate<String, AuditEvent> kafkaTemplate) {
        return switch (properties.getMode()) {
            case CONSOLE -> new ConsoleAuditSender();
            case KAFKA -> new KafkaAuditSender(kafkaTemplate, properties.getKafkaTopic());
        };
    }
}