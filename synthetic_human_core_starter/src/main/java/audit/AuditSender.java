package audit;

import audit.model.AuditEvent;

public interface AuditSender {
    void sendAuditEvent(AuditEvent event);
}