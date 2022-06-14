package by.makei.shop.model.command.impl.user;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.AccessLevel;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.*;
import static by.makei.shop.model.command.RedirectMessage.*;

public class CancelOrderCommand implements Command {
    private static final String ERROR = "CancelOrderCommand Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();

        try {
            if (userService.cancelOrder(request)) {
                User user = (User) session.getAttribute(USER);
                if (user.getAccessLevel().equals(AccessLevel.USER)) {
                    Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
                    optionalUser.ifPresentOrElse(u -> session.setAttribute(USER, u),
                            () -> logger.log(Level.WARN, "user was not found"));
                }
                router.setRedirectType();
                router.setCurrentPage(GO_TO_SHOW_ORDERS+REDIRECT_MESSAGE+UPDATE_SUCCESS);

            } else {
                router.setRedirectType();
                router.setCurrentPage(GO_TO_SHOW_ORDERS+REDIRECT_MESSAGE+UPDATE_FAIL);
                //return false
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Service exception: {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e);
            router.setCurrentPage(ERROR500);
        }
        return router;
    }
}
