package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.util.MessageReinstall;
import jakarta.servlet.http.HttpServletRequest;

import static by.makei.shop.model.command.AttributeName.MESSAGE;
import static by.makei.shop.model.command.PagePath.SHOW_CART;

public class GoToShowCartCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        MessageReinstall.extractAndSetMessage(MESSAGE,request);
        Router router = new Router();
        router.setCurrentPage(SHOW_CART);
        return router;
    }
}
