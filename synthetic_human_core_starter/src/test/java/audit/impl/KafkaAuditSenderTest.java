package audit.impl;

import audit.model.AuditEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class KafkaAuditSenderTest {
    private KafkaTemplate<String, AuditEvent> kafkaTemplateMock;
    private KafkaAuditSender kafkaAuditSender;
    private final String topic = "audit-topic";

    @BeforeEach
    void setUp() {
        kafkaTemplateMock = Mockito.mock(KafkaTemplate.class);
        kafkaAuditSender = new KafkaAuditSender(kafkaTemplateMock, topic);
    }

    @Test
    void sendAuditEvent_shouldCallKafkaTemplateSend() {
        AuditEvent event = new AuditEvent(
                "methodName",
                new Object[]{"param1"},
                "result",
                null
        );

        kafkaAuditSender.sendAuditEvent(event);

        ArgumentCaptor<AuditEvent> captor = ArgumentCaptor.forClass(AuditEvent.class);
        verify(kafkaTemplateMock, times(1))
                .send(eq(topic), captor.capture());

        AuditEvent capturedEvent = captor.getValue();
        assertThat(capturedEvent).isEqualTo(event);
    }
}