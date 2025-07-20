package starter.audit.config;

import starter.audit.AuditSender;
import starter.audit.impl.ConsoleAuditSender;
import starter.audit.impl.KafkaAuditSender;
import starter.audit.model.AuditEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.lang.Nullable;

@Configuration
@EnableConfigurationProperties(AuditProperties.class)
public class AuditAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditSender auditSender(AuditProperties properties,
                                   @Nullable KafkaTemplate<String, AuditEvent> kafkaTemplate) {
        return switch (properties.getMode()) {
            case CONSOLE -> {
                Logger logger = LoggerFactory.getLogger(ConsoleAuditSender.class);
                yield new ConsoleAuditSender(logger);
            }
            case KAFKA -> {
                if (kafkaTemplate == null) {
                    throw new IllegalStateException("KafkaTemplate is required for KAFKA mode");
                }
                yield new KafkaAuditSender(kafkaTemplate, properties.getKafkaTopic());
            }
        };
    }
}