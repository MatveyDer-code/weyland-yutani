package audit.model;

import lombok.Data;

import java.time.Instant;
import java.util.Arrays;

@Data
public class AuditEvent {
    private String methodName;
    private Object[] params;
    private Throwable throwable;
    private Object result;
    private Instant timestamp;

    public AuditEvent(String methodName, Object[] params, Object result, Throwable throwable) {
        this.methodName = methodName;
        this.params = params != null ? Arrays.copyOf(params, params.length) : new Object[0];
        this.throwable = throwable;
        this.result = result;
        this.timestamp = Instant.now();
    }
}