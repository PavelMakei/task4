package by.makei.shop.model.command;

import by.makei.shop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;

    default void refresh(){}; //TODO realize
}
