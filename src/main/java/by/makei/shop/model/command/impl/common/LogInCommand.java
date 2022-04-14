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

public class LogInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        //TODO remake!!!!!!!!!!!!
        //TODO VALIDATOR
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
                logger.log(Level.DEBUG,user);
                router.setCurrentPage(PagePath.MAIN);
            }else {
                request.setAttribute(INVALID_LOGIN_OR_PASSWORD_MESSAGE, INVALID_LOGIN_OR_PASSWORD_MESSAGE);
                router.setCurrentPage(PagePath.LOGIN);
            }

        } catch (ServiceException e) {
            //TODO ?????
            e.printStackTrace();
        }
        return router;
    }
}
