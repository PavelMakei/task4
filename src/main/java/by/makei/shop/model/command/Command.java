package by.makei.shop.model.command;

import by.makei.shop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
public interface Command {
    Logger logger = LogManager.getLogger();

    Router execute(HttpServletRequest request) throws CommandException;

    default void refresh(){}; //TODO realize?
}
