package audit;

import audit.model.AuditEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    private final AuditSender auditSender;

    public AuditAspect(AuditSender auditSender) {
        this.auditSender = auditSender;
    }

    @Around("@annotation(audit.annotation.WeylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();
        Object result = null;
        Throwable throwable = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable t) {
            throwable = t;
            throw t;
        } finally {
            AuditEvent event = new AuditEvent(
                    methodName,
                    args,
                    result,
                    throwable
            );

            auditSender.sendAuditEvent(event);
        }
    }
}
