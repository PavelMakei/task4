package by.makei.shop.command.impl.gotopage;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
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
import java.util.Map;

import static by.makei.shop.command.PagePath.USERS;

public class GoToManageUserCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        List<User> userList;
        Map<User,double[]> userOrderSumMap;
        HttpSession session = request.getSession();
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        logger.log(Level.DEBUG, "GoToManageUserCommand currentPage :{}", currentPage);
        session.setAttribute(AttributeName.CURRENT_PAGE, currentPage);
        ArrayList<AccessLevel> accessLevelList = new ArrayList<>(Arrays.asList(AccessLevel.values()));
        accessLevelList.remove(AccessLevel.GUEST);
        try {
            userOrderSumMap= userService.findAllUserOrderSum();
            request.setAttribute(AttributeName.USER_LIST, userOrderSumMap);
            request.setAttribute(AttributeName.ACCESS_LEVEL_LIST, accessLevelList);
            router.setCurrentPage(USERS);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "GoToManageUserCommand command error. {}", e.getMessage());
            throw new CommandException("GoToManageUserCommand command error", e);
        }
        return router;

    }
}
