package by.makei.shop.model.command.impl.admin;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;

public class UpdateAccessLevelCommand implements Command {
    private static final String ERROR = "UpdateAccessLevelCommand Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(ID, request.getParameter(ID));
        userDataMap.put(ACCESS_LEVEL, request.getParameter(ACCESS_LEVEL));
        HttpSession session = request.getSession();
        String currentPage = session.getAttribute(CURRENT_PAGE).toString();


        try {
            if (parameterValidator.validateUserData(userDataMap)){
                userService.updateAccessLevel(userDataMap);
                router.setRedirectType();
                 router.setCurrentPage(currentPage);
                 //current user?
                User currentUser = (User)session.getAttribute(USER);
                int id = Integer.parseInt(userDataMap.get(ID));
                if(currentUser.getId() == id ){
                    currentUser.setId(id);
                    session.setAttribute(ACCESS_LEVEL, request.getParameter(ACCESS_LEVEL));
                }
            } else {
            logger.log(Level.ERROR, "UpdateAccessLevelCommand incorrect id or access level");
            router.setCurrentPage(ERROR500);
                request.setAttribute(ERROR_MESSAGE,"UpdateAccessLevelCommand incorrect id or access level" );
        }
    } catch (
    ServiceException e) {
        logger.log(Level.ERROR, "UpdateAccessLevelCommand. {}", e.getMessage());
        request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
        router.setCurrentPage(ERROR500);
    }
        return router;

    }
}
