package by.makei.shop.model.command.common;

import by.makei.shop.model.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public String execute(HttpServletRequest request) {
        logger.log(Level.ERROR, "incorrect or null command");
        HttpSession session = request.getSession();
        //TODO как отправить на ситраницу с ошибкой?

        return null;
    }
}
