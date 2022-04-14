package by.makei.shop.model.command;

import by.makei.shop.model.command.impl.common.DefaultCommand;
import by.makei.shop.model.command.impl.common.LogInCommand;
import by.makei.shop.model.command.impl.common.LogOutCommand;
import by.makei.shop.model.command.impl.common.RegistrationCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    LOGIN(new LogInCommand()),
    LOGOUT(new LogOutCommand()),
    REGISTRATION(new RegistrationCommand());

    private static final Logger logger = LogManager.getLogger();

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command defineCommand(String commandName) {
        if (commandName == null || commandName.isEmpty()) {
            logger.log(Level.ERROR, "command is empty");
            return CommandType.valueOf("DEFAULT").getCommand();
            //TODO заменить дефолт на еррор? Зачем две?

        }
        try {
            return CommandType.valueOf(commandName.toUpperCase(Locale.ROOT)).getCommand();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, "incorrect command name. {}", e.getMessage());
            return CommandType.valueOf("ERROR").getCommand();
            //TODO проверить
        }
    }
}
