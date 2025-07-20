package service;

import model.Command;

public interface CommandExecutor {
    void execute(Command command);
}
