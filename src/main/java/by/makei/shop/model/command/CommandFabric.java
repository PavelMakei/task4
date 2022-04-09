package by.makei.shop.model.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public class CommandFabric {
    private static final Logger logger = LogManager.getLogger();

    private  CommandFabric () {}

    public static Command defineCommand(String commandName){
        if (commandName == null){
            logger.log(Level.ERROR, "command is empty");
            //TODO go to main page? Throw exception?
        }
        return CommandType.valueOf(commandName.toUpperCase(Locale.ROOT)).getCommand();
    }
}
