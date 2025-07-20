package audit.model;

import lombok.Data;

import java.time.Instant;
import java.util.Arrays;

@Data
public class AuditEvent {
    private String methodName;
    private Object[] params;
    private Object result;
    private Instant timestamp;

    public AuditEvent(String methodName, Object[] params, Object result) {
        this.methodName = methodName;
        this.params = params != null ? Arrays.copyOf(params, params.length) : new Object[0];
        this.result = result;
        this.timestamp = Instant.now();
    }
}