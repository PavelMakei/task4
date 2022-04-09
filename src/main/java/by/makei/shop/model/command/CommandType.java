package by.makei.shop.model.command;

import by.makei.shop.model.command.common.DefaultCommand;
import by.makei.shop.model.command.common.LogInCommand;
import by.makei.shop.model.command.common.LogOutCommand;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    LOGIN(new LogInCommand()),
    LOGOUT(new LogOutCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
