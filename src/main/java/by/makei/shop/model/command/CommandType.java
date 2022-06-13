package by.makei.shop.model.command;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.impl.admin.AddNewProductCommand;
import by.makei.shop.model.command.impl.admin.UpdateAccessLevelCommand;
import by.makei.shop.model.command.impl.admin.UpdatePhotoCommand;
import by.makei.shop.model.command.impl.admin.UpdateProductDataCommand;
import by.makei.shop.model.command.impl.common.*;
import by.makei.shop.model.command.impl.gotopage.*;
import by.makei.shop.model.command.impl.user.*;
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
    SHOW_PRODUCT(new ShowProductCommand()),
    GO_UPDATE_PRODUCT(new GoToUpdateProductCommand()),
    UPDATE_PRODUCT_DATA(new UpdateProductDataCommand()),
    UPDATE_PHOTO(new UpdatePhotoCommand()),
    UPDATE_ACCESS_LEVEL(new UpdateAccessLevelCommand()),
    GO_TO_MANAGE_USER(new GoToManageUserCommand()),
    GO_TO_UPDATE_PROFILE(new GoToUpdateProfileCommand()),
    REGISTRATION_SEND_ACTIVATION_CODE(new RegistrationSendActivationCodeCommand()),
    RECOVERY_SEND_ACTIVATION_CODE(new RecoverySendActivationCode()),
    UPDATE_PASSWORD(new UpdatePasswordCommand()),
    DEPOSIT_MONEY(new DepositMoneyCommand()),
    GO_TO_DEPOSIT_MONEY(new GoToDepositMoneyCommand()),
    GO_TO_SHOW_CART(new GoToShowCartCommand()),
    ADD_TO_CART(new AddToCartCommand()),
    CLEAR_CART(new ClearCartCommand()),
    GO_TO_CHECKOUT(new GoToCheckoutCommand()),
    GO_TO_SHOW_ORDER(new GoToShowOrderCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    UPDATE_PROFILE(new UpdateProfileCommand());

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
            logger.log(Level.ERROR, "Command is null");
            return CommandType.DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(commandName[0].toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, "Incorrect or empty command name. {}", e.getMessage());
           throw new CommandException("Incorrect or empty command name.", e);
        }
    }
}
