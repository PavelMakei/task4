package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.PagePath;
import by.makei.shop.model.command.RedirectMessage;
import by.makei.shop.model.command.Router;
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

public class LogInCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();

        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserService userService = UserServiceImpl.getInstance();
        try {
            Optional<User> optionalUser = userService.signIn(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                session.setAttribute(ACCESS_LEVEL, user.getAccessLevel().toString());
                logger.log(Level.DEBUG, "attributes for user {} were set.", user);
                router.setRedirectType();//f5 defence
                if (user.getAccessLevel().toString().equals("BLOCKED")) {
                    router.setCurrentPage(BLOCKED_USER);
                    session.setAttribute(CURRENT_PAGE, BLOCKED_USER);
                } else {
                    router.setCurrentPage(GO_TO_MAIN + REDIRECT_MESSAGE + USER_WELCOME);
                }

            } else {
                logger.log(Level.INFO, "user wasn't found.");
                request.setAttribute(MESSAGE, RedirectMessage.INVALID_LOGIN_OR_PASSWORD);
                router.setCurrentPage(PagePath.LOGINATION);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "login command error. {}", e.getMessage());
            throw new CommandException("loginCommand command error",e);
        }
        return router;
    }
}
