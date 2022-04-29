package by.makei.shop.model.command;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.impl.admin.AddNewProductCommand;
import by.makei.shop.model.command.impl.common.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    LOGIN(new LogInCommand()),
    LOGOUT(new LogOutCommand()),
    REGISTRATION(new RegistrationCommand()),
    ADD_NEW_PRODUCT(new AddNewProductCommand()),
    GO_TO_ADD_NEW_PRODUCT(new GoToAddNewProduct()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand());


    private static final Logger logger = LogManager.getLogger();

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command defineCommand(String commandName) throws CommandException {
        if (commandName == null ) {
            logger.log(Level.INFO, "command is null");
            return CommandType.DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(commandName.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, "incorrect or empty command name. {}", e.getMessage());
           throw new CommandException(e.getMessage());
        }
    }
}
