package model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.CommandExecutorImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CommandExecutorImplTest {
    private static Validator validator;
    private static CommandExecutorImpl executor;

    private static org.slf4j.Logger loggerMock;

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        loggerMock = Mockito.mock(org.slf4j.Logger.class);
        int queueCapacity = 2;
        executor = new CommandExecutorImpl(validator, queueCapacity, loggerMock);
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

    @Test
    void criticalCommandExecutesImmediately() {
        Command criticalCommand = new Command(
                "Check power unit",
                CommandPriority.CRITICAL,
                "Ripley",
                LocalDate.now()
        );

        executor.execute(criticalCommand);

        // Проверяем, что логгер получил вызов info с текстом, содержащим "CRITICAL"
        verify(loggerMock, times(1)).info(contains("CRITICAL"));
    }

    @Test
    void commonCommandThrowsExceptionIfQueueIsFull() {
        for (int i = 0; i < 2; i++) {
            Command commonCommand = new Command(
                    "Check power unit",
                    CommandPriority.COMMON,
                    String.valueOf(i),
                    LocalDate.now()
            );
            executor.execute(commonCommand);
            executor.getQueueSize();
            System.out.println(commonCommand.toString() + " " + executor.getQueueSize());
        }

        // Проверяем, что очередь забита и мы добавляем ещё одну "COMMON" команду
        assertEquals(2, executor.getQueueSize());
        Command commonCommand = new Command(
                "Check power unit",
                CommandPriority.COMMON,
                "Exception",
                LocalDate.now()
        );
        assertThrows(IllegalStateException.class, () -> executor.execute(commonCommand));
    }
}