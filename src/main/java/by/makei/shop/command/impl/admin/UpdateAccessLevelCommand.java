package by.makei.shop.command.impl.admin;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.command.PagePath.ERROR500;

public class UpdateAccessLevelCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(AttributeName.ID, request.getParameter(AttributeName.ID));
        userDataMap.put(AttributeName.ACCESS_LEVEL, request.getParameter(AttributeName.ACCESS_LEVEL));
        HttpSession session = request.getSession();
        String currentPage = session.getAttribute(AttributeName.CURRENT_PAGE).toString();

        try {
            if (userService.updateAccessLevel(userDataMap)) {
                router.setCurrentPage(currentPage);
                //current user?
                User currentUser = (User) session.getAttribute(AttributeName.USER);
                int id = Integer.parseInt(userDataMap.get(AttributeName.ID));
                if (currentUser.getId() == id) {
                    currentUser = userService.findUserByOneParam(AttributeName.ID, String.valueOf(id)).get();
                    session.setAttribute(AttributeName.ACCESS_LEVEL, request.getParameter(AttributeName.ACCESS_LEVEL));
                    session.setAttribute(AttributeName.USER, currentUser);
                }
            } else {
                logger.log(Level.ERROR, "UpdateAccessLevelCommand incorrect id or access level");
                router.setCurrentPage(ERROR500);
                request.setAttribute(AttributeName.ERROR_MESSAGE, "UpdateAccessLevelCommand incorrect id or access level");
            }
        } catch (
                ServiceException e) {
            logger.log(Level.ERROR, "UpdateAccessLevelCommand command error {}", e.getMessage());
            throw new CommandException("UpdateAccessLevelCommand command error", e);
        }
        return router;

    }
}
