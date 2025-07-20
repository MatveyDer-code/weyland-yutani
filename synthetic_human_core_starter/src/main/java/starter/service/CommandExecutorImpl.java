package starter.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import starter.model.Command;
import org.slf4j.Logger;
import starter.model.CommandPriority;

import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandExecutorImpl implements CommandExecutor {
    private final Validator validator;
    private final BlockingQueue<Command> queue;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Logger logger;

    public CommandExecutorImpl(Validator validator, int queueCapacity, Logger logger) {
        this.validator = validator;
        this.queue = new ArrayBlockingQueue<>(queueCapacity);
        this.logger = logger;
    }

    public void startQueueProcessor() {
        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(500);
                    Command cmd = queue.take();
                    logger.info("Executing COMMON command: " + cmd);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @Override
    public void execute(Command command) {
        Set<ConstraintViolation<Command>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid command: " + violations);
        }

        if (command.getPriority() == CommandPriority.CRITICAL) {
            // Немедленное исполнение CRITICAL команды
            logger.info("Executing CRITICAL command: " + command);
        } else {
            // COMMON команды кладем в очередь
            if (queue.remainingCapacity() <= 0) {
                throw new IllegalStateException("Command queue is full");
            } else {
                queue.add(command);
                logger.info("Added COMMON command to queue: " + command);
            }
        }
    }

    public int getQueueSize() {
        return queue.size();
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}