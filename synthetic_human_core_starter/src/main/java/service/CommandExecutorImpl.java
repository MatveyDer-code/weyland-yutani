package service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import model.Command;

import java.util.Set;

public class CommandExecutorImpl implements CommandExecutor {
    private final Validator validator;
    private final int queueCapacity;

    public CommandExecutorImpl(Validator validator, int queueCapacity) {
        this.validator = validator;
        this.queueCapacity = queueCapacity;
    }

    @Override
    public void execute(Command command) {
        Set<ConstraintViolation<Command>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid command: " + violations);
        }
    }
}