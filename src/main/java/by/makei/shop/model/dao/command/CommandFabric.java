package by.makei.shop.model.dao.command;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public class CommandFabric {
    private static final Logger logger = LogManager.getLogger();

    private  CommandFabric () {}

    public static Command defineCommand(HttpServletRequest request){
        String command = request.getParameter(Parameter.COMMAND);
        if (command == null){
            logger.log(Level.ERROR, "command is empty");
            //TODO go to main? Throw exception?
        }
        return CommandType.valueOf(command.toUpperCase(Locale.ROOT)).getCommand();
    }
}
