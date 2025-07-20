package audit.impl;

import audit.AuditSender;
import audit.model.AuditEvent;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaAuditSender implements AuditSender {
    private final KafkaTemplate<String, AuditEvent> kafkaTemplate;
    private final String topic;

    public KafkaAuditSender(KafkaTemplate<String, AuditEvent> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void sendAuditEvent(AuditEvent event) {
        kafkaTemplate.send(topic, event);
    }
}