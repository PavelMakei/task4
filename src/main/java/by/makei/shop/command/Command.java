package by.makei.shop.command;

import by.makei.shop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@code Command} interface represent functional of command
 */
@FunctionalInterface
public interface Command {
    Logger logger = LogManager.getLogger();

    /***
     * Execute command
     * @param request
     * @return router {@link Router}
     * @throws CommandException if error while execute or catch some ServiceException, {@link CommandException}
     */
    Router execute(HttpServletRequest request) throws CommandException;


}
