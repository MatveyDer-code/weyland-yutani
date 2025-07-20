package audit.impl;

import audit.AuditSender;
import audit.model.AuditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleAuditSender implements AuditSender {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleAuditSender.class);

    @Override
    public void sendAuditEvent(AuditEvent event) {
        logger.info("AUDIT (console): {}", event);
    }
}