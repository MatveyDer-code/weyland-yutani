package audit;

import audit.model.AuditEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class AuditAspectTest {

    @Mock
    private AuditSender senderMock;

    @InjectMocks
    private AuditAspect auditAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuditAspectInterceptsAnnotatedMethod() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        MethodSignature methodSignature = mock(MethodSignature.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getName()).thenReturn("someMethod");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"param", 41});
        when(joinPoint.proceed()).thenReturn("result");

        Object result = auditAspect.auditMethod(joinPoint);

        assertEquals("result", result);

        ArgumentCaptor<AuditEvent> captor = ArgumentCaptor.forClass(AuditEvent.class);
        verify(senderMock).sendAuditEvent(captor.capture());

        AuditEvent event = captor.getValue();
        assertEquals("someMethod", event.getMethodName());
        assertArrayEquals(new Object[]{"param", 41}, event.getParams());
        assertEquals("result", event.getResult());
    }
}