package by.makei.shop.model.command.common;

import by.makei.shop.model.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
