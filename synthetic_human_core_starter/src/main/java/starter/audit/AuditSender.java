package starter.audit;

import starter.audit.model.AuditEvent;

public interface AuditSender {
    void sendAuditEvent(AuditEvent event);
}