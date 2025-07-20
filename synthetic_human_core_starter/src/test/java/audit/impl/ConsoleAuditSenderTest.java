package audit.impl;

import audit.model.AuditEvent;
import com.github.valfirst.slf4jtest.TestLogger;
import com.github.valfirst.slf4jtest.TestLoggerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleAuditSenderTest {
    private ConsoleAuditSender auditSender;
    private TestLogger testLogger;

    @BeforeEach
    void setUp() {
        auditSender = new ConsoleAuditSender();
        testLogger = TestLoggerFactory.getTestLogger(ConsoleAuditSender.class);
        testLogger.clear();
    }

    @Test
    void shouldLogAuditEvent() {
        AuditEvent event = new AuditEvent("testMethod", new Object[]{"param"}, "result", null);

        auditSender.sendAuditEvent(event);

        assertThat(testLogger.getLoggingEvents())
                .anyMatch(loggingEvent ->
                        loggingEvent.getMessage().contains("AUDIT (console):") &&
                        loggingEvent.getArguments().contains(event));

    }
}