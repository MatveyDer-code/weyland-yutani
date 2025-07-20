package starter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CommandControllerErrorHandling {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("message", "Validation failed: " + ex.getBindingResult().getErrorCount() + " error(s)");
        errorBody.put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}