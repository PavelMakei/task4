package by.makei.shop.model.command.impl.common;

import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

public class DefaultCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        logger.log(Level.INFO, "null or empty command");
        return new Router();
    }
}
