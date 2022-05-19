package by.makei.shop.model.command;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.impl.admin.AddNewProductCommand;
import by.makei.shop.model.command.impl.common.*;
import by.makei.shop.model.command.impl.gotopage.GoToAddNewProductCommand;
import by.makei.shop.model.command.impl.gotopage.GoToAddNewUserCommand;
import by.makei.shop.model.command.impl.gotopage.GoToLoginCommand;
import by.makei.shop.model.command.impl.gotopage.GoToMainCommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.makei.shop.model.command.AttributeName.COMMAND;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    LOGIN(new LogInCommand()),
    LOGOUT(new LogOutCommand()),
    REGISTRATION(new RegistrationCommand()),
    ADD_NEW_PRODUCT(new AddNewProductCommand()),
    GO_TO_ADD_NEW_PRODUCT(new GoToAddNewProductCommand()),
    GO_TO_ADD_NEW_USER(new GoToAddNewUserCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    GO_TO_LOGIN(new GoToLoginCommand()),
    GO_TO_MAIN(new GoToMainCommand()),
    SHOW_PRODUCT(new ShowProductCommand());


    private static final Logger logger = LogManager.getLogger();

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command defineCommand(HttpServletRequest request) throws CommandException {
        Map<String,String[]> parameterMap = request.getParameterMap();

        String[] commandName = parameterMap.get(COMMAND);
        if (commandName == null ) {
            logger.log(Level.INFO, "command is null");
            return CommandType.DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(commandName[0].toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, "incorrect or empty command name. {}", e.getMessage());
           throw new CommandException(e.getMessage());
        }
    }
}
