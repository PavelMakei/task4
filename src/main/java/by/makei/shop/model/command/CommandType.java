package by.makei.shop.model.command;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.impl.admin.*;
import by.makei.shop.model.command.impl.common.*;
import by.makei.shop.model.command.impl.gotopage.*;
import by.makei.shop.model.command.impl.user.*;
import by.makei.shop.model.entity.AccessLevel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.COMMAND;

public enum CommandType {
    //TODO дописать уровни доступа
    DEFAULT(new DefaultCommand(), EnumSet.of(AccessLevel.ADMIN)),
    LOGIN(new LogInCommand(), EnumSet.of(AccessLevel.ADMIN)),
    LOGOUT(new LogOutCommand(), EnumSet.of(AccessLevel.ADMIN)),
    REGISTRATION(new RegistrationCommand(), EnumSet.of(AccessLevel.ADMIN)),
    ADD_NEW_PRODUCT(new AddNewProductCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_TO_ADD_NEW_PRODUCT(new GoToAddNewProductCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_TO_ADD_NEW_USER(new GoToAddNewUserCommand(), EnumSet.of(AccessLevel.ADMIN)),
    CHANGE_LANGUAGE(new ChangeLanguageCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_TO_LOGIN(new GoToLoginCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_TO_MAIN(new GoToMainCommand(), EnumSet.of(AccessLevel.ADMIN)),
    SHOW_PRODUCT(new ShowProductCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_UPDATE_PRODUCT(new GoToUpdateProductCommand(), EnumSet.of(AccessLevel.ADMIN)),
    UPDATE_PRODUCT_DATA(new UpdateProductDataCommand(), EnumSet.of(AccessLevel.ADMIN)),
    UPDATE_PHOTO(new UpdatePhotoCommand(), EnumSet.of(AccessLevel.ADMIN)),
    UPDATE_ACCESS_LEVEL(new UpdateAccessLevelCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_TO_MANAGE_USER(new GoToManageUserCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_TO_UPDATE_PROFILE(new GoToUpdateProfileCommand(), EnumSet.of(AccessLevel.ADMIN)),
    REGISTRATION_SEND_ACTIVATION_CODE(new RegistrationSendActivationCodeCommand(), EnumSet.of(AccessLevel.ADMIN)),
    RECOVERY_SEND_ACTIVATION_CODE(new RecoverySendActivationCode(), EnumSet.of(AccessLevel.ADMIN)),
    UPDATE_PASSWORD(new UpdatePasswordCommand(), EnumSet.of(AccessLevel.ADMIN)),
    DEPOSIT_MONEY(new DepositMoneyCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_TO_DEPOSIT_MONEY(new GoToDepositMoneyCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_TO_SHOW_CART(new GoToShowCartCommand(), EnumSet.of(AccessLevel.ADMIN)),
    ADD_TO_CART(new AddToCartCommand(), EnumSet.of(AccessLevel.ADMIN)),
    CLEAR_CART(new ClearCartCommand(), EnumSet.of(AccessLevel.ADMIN)),
    GO_TO_CHECKOUT(new GoToCheckoutCommand(), EnumSet.of(AccessLevel.ADMIN)),
    SHOW_ORDER(new ShowOrderCommand(), EnumSet.of(AccessLevel.ADMIN)),
    CREATE_ORDER(new CreateOrderCommand(), EnumSet.of(AccessLevel.ADMIN)),
    CANCEL_ORDER(new CancelOrderCommand(), EnumSet.of(AccessLevel.ADMIN)),
    DELIVER_ORDER(new DeliverOrderCommand(), EnumSet.of(AccessLevel.ADMIN)),
    UPDATE_PROFILE(new UpdateProfileCommand(), EnumSet.of(AccessLevel.ADMIN));

    private static final Logger logger = LogManager.getLogger();

//    private Command command;


     CommandType(Command command, EnumSet<AccessLevel> accessLevels) {
        this.command = command;
        this.accessLevels = accessLevels;
    }
    public Command getCommand() {
        return command;
    }

    public EnumSet<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    private Command command;
    EnumSet<AccessLevel> accessLevels;

    public static EnumSet<AccessLevel> defineCommandAccessLevel(String commandName) throws CommandException {
        if (commandName == null ) {
            logger.log(Level.ERROR, "Command is null");
            return  EnumSet.noneOf(AccessLevel.class);
        }
        try {
            return CommandType.valueOf(commandName.toUpperCase()).getAccessLevels();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, "Incorrect or empty command name. {}", e.getMessage());
            throw new CommandException("Incorrect or empty command name.", e);
        }
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
