package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.PagePath;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.*;

public class LogInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ERROR = "LoginCommand Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        //TODO add cookies?

        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserService userService = UserServiceImpl.getInstance();
        try{
            Optional<User> optionalUser = userService.signIn(login,password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                session.setAttribute(ACCESS_LEVEL, user.getAccessLevel());
                logger.log(Level.DEBUG,"attributes for user {} were set.", user);
                router.setCurrentPage(PagePath.MAIN);
                //TODO переход на main?
            }else {
                logger.log(Level.INFO,"user wasn't found.");
                request.setAttribute(INVALID_LOGIN_OR_PASSWORD_MESSAGE, INVALID_LOGIN_OR_PASSWORD_MESSAGE);
                router.setCurrentPage(PagePath.LOGINATION);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR,"login command error. {}",e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
        }
        return router;
    }
}
