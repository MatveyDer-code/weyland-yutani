package starter.service;

import starter.model.Command;

public interface CommandExecutor {
    void execute(Command command);
}
