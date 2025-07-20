package model;

import model.Command;
import model.CommandPriority;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.CommandExecutor;
import service.CommandExecutorImpl;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommandExecutorImplTest {
    private static Validator validator;
    private static CommandExecutor executor;

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        int queueCapacity = 10;
        executor = new CommandExecutorImpl(validator, queueCapacity);
    }

    @Test
    void shouldThrowExceptionWhenCommandIsInvalid() {
        Command invalidCommand = new Command("", null, "", null);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> executor.execute(invalidCommand)
        );

        assertTrue(thrown.getMessage().contains("Invalid command"));
    }

    @Test
    void shouldAcceptValidCommand() {
        Command validCommand = new Command(
                "SAY_HELLO",
                CommandPriority.CRITICAL,
                "Hello, world!",
                LocalDate.now()
        );

        assertDoesNotThrow(() -> executor.execute(validCommand));
    }
}