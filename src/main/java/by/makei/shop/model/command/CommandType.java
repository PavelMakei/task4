package by.makei.shop.model.command;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.impl.admin.AddNewProduct;
import by.makei.shop.model.command.impl.common.*;
import by.makei.shop.model.command.impl.gotopage.GoToAddNewProduct;
import by.makei.shop.model.command.impl.gotopage.GoToAddNewUser;
import by.makei.shop.model.command.impl.gotopage.GoToLogin;
import by.makei.shop.model.command.impl.gotopage.GoToMain;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.makei.shop.model.command.AttributeName.COMMAND;

public enum CommandType {
    DEFAULT(new Default()),
    LOGIN(new LogIn()),
    LOGOUT(new LogOut()),
    REGISTRATION(new Registration()),
    ADD_NEW_PRODUCT(new AddNewProduct()),
    GO_TO_ADD_NEW_PRODUCT(new GoToAddNewProduct()),
    GO_TO_ADD_NEW_USER(new GoToAddNewUser()),
    CHANGE_LANGUAGE(new ChangeLanguage()),
    GO_TO_LOGIN(new GoToLogin()),
    GO_TO_MAIN(new GoToMain()),
    SHOW_PRODUCT(new ShowProduct());


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
