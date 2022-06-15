package by.makei.shop.model.command;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.impl.common.AddToCartCommand;
import by.makei.shop.model.command.impl.common.DefaultCommand;
import by.makei.shop.model.command.impl.gotopage.GoToCheckoutCommand;
import by.makei.shop.model.entity.AccessLevel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.EnumSet;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.COMMAND;

public enum CommandTypeTemp {
    ADD_SOME_COMMAND(new AddToCartCommand(), EnumSet.of(AccessLevel.ADMIN)),
    DEFAULT(new DefaultCommand(), EnumSet.of(AccessLevel.ADMIN)),
    ADD_SECOND_COMMAND(new GoToCheckoutCommand(), EnumSet.of(AccessLevel.GUEST, AccessLevel.USER));


    private CommandTypeTemp(Command command, EnumSet<AccessLevel> accessLevels) {
        this.command = command;
        this.accessLevels = accessLevels;
    }
    public Command getCommand() {
        return command;
    }

    private Command command;
    EnumSet<AccessLevel> accessLevels;

//    public static Command defineCommand(HttpServletRequest request) throws CommandException {
    public static Command defineCommand(String[] commandName) throws CommandException {
//        Map<String,String[]> parameterMap = request.getParameterMap();

//        String[] commandName = parameterMap.get(COMMAND);
        if (commandName == null ) {
//            logger.log(Level.ERROR, "Command is null");
            return CommandTypeTemp.DEFAULT.getCommand();
        }
        try {
            return CommandTypeTemp.valueOf(commandName[0].toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
//            logger.log(Level.ERROR, "Incorrect or empty command name. {}", e.getMessage());
            throw new CommandException("Incorrect or empty command name.", e);
        }
    }

}
