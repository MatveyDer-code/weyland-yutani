package audit.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import starter.audit.impl.ConsoleAuditSender;
import starter.audit.model.AuditEvent;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.contains;

class ConsoleAuditSenderTest {

    @Test
    void shouldLogAuditEvent() {
        Logger mockLogger = Mockito.mock(Logger.class);
        ConsoleAuditSender auditSender = new ConsoleAuditSender(mockLogger);

        AuditEvent event = new AuditEvent("testMethod", new Object[]{"param"}, "result", null);

        auditSender.sendAuditEvent(event);

        verify(mockLogger).info(contains("AUDIT (console):"), Mockito.eq(event));
    }
}