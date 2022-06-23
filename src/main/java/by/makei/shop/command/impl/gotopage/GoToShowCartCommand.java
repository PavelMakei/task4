package by.makei.shop.command.impl.gotopage;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.util.MessageReinstall;
import jakarta.servlet.http.HttpServletRequest;

import static by.makei.shop.command.PagePath.SHOW_CART;

public class GoToShowCartCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MessageReinstall.extractAndSetMessage(AttributeName.MESSAGE, request);
        Router router = new Router();
        router.setCurrentPage(SHOW_CART);
        return router;
    }
}
