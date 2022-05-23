package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.AccessLevel;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;
import static by.makei.shop.model.command.PagePath.USERS;

public class GoToManageUserCommand implements Command {
    private static final String ERROR = "GoToManageUserCommand Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        List<User> userList;
        HttpSession session = request.getSession();
        String currentPage = PagePathExtractor.extractPagePath(request);
        session.setAttribute(CURRENT_PAGE, currentPage);
        ArrayList<AccessLevel> accessLevelList = new ArrayList<>(Arrays.asList(AccessLevel.values()));
        accessLevelList.remove(AccessLevel.GUEST);
        try {
            userList = userService.findAllUser();
            request.setAttribute(USER_LIST, userList);
            request.setAttribute(ACCESS_LEVEL_LIST, accessLevelList);
            router.setCurrentPage(USERS);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "GoToManageUserCommand command error. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
        }
        return router;

    }
}
