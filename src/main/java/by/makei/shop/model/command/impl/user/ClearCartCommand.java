package by.makei.shop.model.command.impl.user;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.Cart;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.makei.shop.model.command.AttributeName.SESS_CART;
import static by.makei.shop.model.command.PagePath.GO_TO_MAIN;
import static by.makei.shop.model.command.RedirectMessage.*;

public class ClearCartCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(SESS_CART);
        cart.clear();
        Router router = new Router();
        router.setRedirectType();
        router.setCurrentPage(GO_TO_MAIN+REDIRECT_MESSAGE+CART_IS_EMPTY);
        return router;
    }
}
