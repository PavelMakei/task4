package by.makei.shop.model.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public class CommandFabric {
    private static final Logger logger = LogManager.getLogger();

    private  CommandFabric () {}

    public static Command defineCommand(String commandName){
        if (commandName == null || commandName.isEmpty()){
            logger.log(Level.ERROR, "command is empty");
            return CommandType.valueOf("DEFAULT").getCommand();

        }
        try{
            return CommandType.valueOf(commandName.toUpperCase(Locale.ROOT)).getCommand();
        }catch (IllegalArgumentException e){
           logger.log(Level.ERROR,"incorrect command name. {}", e.getMessage());
            //TODO go to main page? Throw exception?
            return CommandType.valueOf("DEFAULT").getCommand();
        }
    }
}
