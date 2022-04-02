package by.makei.shop.model.command;

import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    String execute(HttpServletRequest request);

    default void refresh(){}; //TODO realize
}
