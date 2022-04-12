package by.makei.shop.model.command.common;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.PagePath;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.dao.impl.UserDaoImpl;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.makei.shop.model.command.Parameter.*;

public class LogInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        //TODO remade!!!!!!!!!!!!
        //TODO VALIDATOR
        String page = null;
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserService userService = new UserServiceImpl();//receiver вопрос в месте инициализации. Здесь, либо как в примере Блинова (new Command(new Service))
        try {
            Optional<User> optionalUser = userService.signIn(login,password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                session.setAttribute(ACCESS_LEVEL, user.getAccessLevel());

                //TODO!!!!!!!!!
                System.out.println(user);
                router.setCurrentPage(PagePath.MAIN);
            }else {
                session.setAttribute(MESSAGE, "incorrect");
                router.setCurrentPage(PagePath.LOGIN);
            }

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return router;
    }
}
