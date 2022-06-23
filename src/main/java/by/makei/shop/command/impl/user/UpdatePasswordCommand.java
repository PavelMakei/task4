package by.makei.shop.command.impl.user;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.command.PagePath.LOGINATION;
import static by.makei.shop.command.PagePath.PASSWORD_RECOVERY;

public class UpdatePasswordCommand implements Command {
    private static final String ERROR = "UpdatePasswordCommand Service exception : ";
    private static final String PASSWORD_UPDATED = "password.updated";
    private static final String PASSWORD_NOT_UPDATED = "password.not.updated";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(AttributeName.EMAIL, request.getParameter(AttributeName.EMAIL));
        userDataMap.put(AttributeName.PASSWORD, request.getParameter(AttributeName.PASSWORD));
        userDataMap.put(AttributeName.ACTIVATION_CODE, request.getParameter(AttributeName.ACTIVATION_CODE));
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        try {
            if (parameterValidator.validateAndMarkUserData(userDataMap)
                && userService.findUserByEmail(userDataMap.get(AttributeName.EMAIL)).isPresent()
                && parameterValidator.validateAndMarkActivationCodeAndSavedEmail(userDataMap, session)) {
                userService.updatePassword(userDataMap);
                session.removeAttribute(AttributeName.SESS_EMAIL);
                session.removeAttribute(AttributeName.SESS_ACTIVATION_CODE);
                //send email notification?
                //authorize user?
                request.setAttribute(AttributeName.MESSAGE, PASSWORD_UPDATED);
                router.setCurrentPage(LOGINATION);

            } else {
                logger.log(Level.INFO, "UpdatePasswordCommand incorrect input data");
                request.setAttribute(AttributeName.MESSAGE, PASSWORD_NOT_UPDATED);
                router.setCurrentPage(PASSWORD_RECOVERY);
                for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "UpdatePasswordCommand command error. {}", e.getMessage());
            throw new CommandException("UpdatePasswordCommand command error", e);
        }
        return router;
    }
}
