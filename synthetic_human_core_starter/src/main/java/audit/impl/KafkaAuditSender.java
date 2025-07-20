package audit.impl;

import audit.AuditSender;
import audit.model.AuditEvent;

public class KafkaAuditSender implements AuditSender {

    @Override
    public void sendAuditEvent(AuditEvent event) {
        // TODO: Реальная отправка в Kafka
    }
}