package by.makei.shop.command.impl.admin;

import by.makei.shop.command.Command;
import by.makei.shop.command.RedirectMessage;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import static by.makei.shop.command.PagePath.GO_TO_SHOW_ORDERS;

public class DeliverOrderCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        try {
            if (userService.deliveryOrder(request)) {
                router.setRedirectType();
                router.setCurrentPage(GO_TO_SHOW_ORDERS + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.UPDATE_SUCCESS);
            } else {
                logger.log(Level.INFO, "incorrect input date. Cancel command");
                router.setRedirectType();
                router.setCurrentPage(GO_TO_SHOW_ORDERS + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.UPDATE_FAIL);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Service exception: {}", e.getMessage());
            throw new CommandException("DeliverOrderCommand command error", e);
        }
        return router;
    }
}
