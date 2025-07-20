package starter.audit.impl;

import starter.audit.AuditSender;
import starter.audit.model.AuditEvent;
import org.slf4j.Logger;

public class ConsoleAuditSender implements AuditSender {
    private final Logger logger;

    public ConsoleAuditSender(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void sendAuditEvent(AuditEvent event) {
        logger.info("AUDIT (console): {}", event);
    }
}