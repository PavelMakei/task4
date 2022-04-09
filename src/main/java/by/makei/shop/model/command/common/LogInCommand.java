package by.makei.shop.model.command.common;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.dao.impl.UserDaoImpl;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.makei.shop.model.command.Parameter.USER;

public class LogInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserService userService = new UserServiceImpl();
        try {
            Optional<User> optionalUser = userService.signIn(login,password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                //TODO!!!!!!!!!
                System.out.println(user);
                page = "/view/pages/login.jsp";
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }



        return page;
    }
}
