package by.makei.shop.model.command;

import by.makei.shop.model.command.common.DefaultCommand;
import by.makei.shop.model.command.common.LogInCommand;
import by.makei.shop.model.command.common.LogOutCommand;

public enum CommandType {
    DEFAULT(new DefaultCommand());
    LOG_IN(new LogInCommand());
    LOG_OUT(new LogOutCommand());

}
