package by.makei.shop.command.impl.common;

import by.makei.shop.command.*;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.Optional;

import static by.makei.shop.command.PagePath.BLOCKED_USER;
import static by.makei.shop.command.PagePath.GO_TO_MAIN;

public class LogInCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();

        HttpSession session = request.getSession();
        String login = request.getParameter(AttributeName.LOGIN);
        String password = request.getParameter(AttributeName.PASSWORD);
        UserService userService = UserServiceImpl.getInstance();
        try {
            Optional<User> optionalUser = userService.signIn(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(AttributeName.USER, user);
                session.setAttribute(AttributeName.ACCESS_LEVEL, user.getAccessLevel().toString());
                logger.log(Level.DEBUG, "attributes for user {} were set.", user);
                router.setRedirectType();//f5 defence
                if (user.getAccessLevel().toString().equals("BLOCKED")) {
                    router.setCurrentPage(BLOCKED_USER);
                    session.setAttribute(AttributeName.CURRENT_PAGE, BLOCKED_USER);
                } else {
                    router.setCurrentPage(GO_TO_MAIN + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.USER_WELCOME);
                }

            } else {
                logger.log(Level.INFO, "user wasn't found.");
                request.setAttribute(AttributeName.MESSAGE, RedirectMessage.INVALID_LOGIN_OR_PASSWORD);
                router.setCurrentPage(PagePath.LOGINATION);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "login command error. {}", e.getMessage());
            throw new CommandException("loginCommand command error", e);
        }
        return router;
    }
}
