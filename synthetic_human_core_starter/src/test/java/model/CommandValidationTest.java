package model;

import starter.model.Command;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CommandValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationForAllInvalidFields() {
        Command command = new Command();
        command.setDescription(""); // Нарушает @NotBlank
        command.setPriority(null);   // Нарушает @NotNull
        command.setAuthor("");       // Нарушает @NotBlank
        command.setDate(null);       // Нарушает @NotNull

        Set<ConstraintViolation<Command>> violations = validator.validate(command);

        assertThat(violations)
                .isNotEmpty()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description"))
                .anyMatch(v -> v.getPropertyPath().toString().equals("priority"))
                .anyMatch(v -> v.getPropertyPath().toString().equals("author"))
                .anyMatch(v -> v.getPropertyPath().toString().equals("date"));
    }
}