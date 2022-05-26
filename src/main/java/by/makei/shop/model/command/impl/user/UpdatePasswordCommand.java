package by.makei.shop.model.command.impl.user;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.AttributeName.ACTIVATION_CODE;
import static by.makei.shop.model.command.PagePath.ERROR500;
import static by.makei.shop.model.command.PagePath.PASSWORD_RECOVERY;

public class UpdatePasswordCommand implements Command {
    private static final String ERROR = "UpdatePasswordCommand Service exception : ";
    private static final String PASSWORD_UPDATED = "password.updated";
    private static final String PASSWORD_NOT_UPDATED = "password.not.updated";


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(EMAIL, request.getParameter(EMAIL));
        userDataMap.put(PASSWORD, request.getParameter(PASSWORD));
        userDataMap.put(ACTIVATION_CODE, request.getParameter(ACTIVATION_CODE));
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        try {
            if (parameterValidator.validateAndMarkUserData(userDataMap)
                && userService.findUserByEmail(userDataMap.get(EMAIL)).isPresent()
                && parameterValidator.validateAndMarkActivationCodeAndSavedEmail(userDataMap, session)) {
                //TODO проверять ответ?
                userService.updatePassword(userDataMap);
                session.removeAttribute(SESS_EMAIL);
                session.removeAttribute(SESS_ACTIVATION_CODE);
                //TODO !!!! почистить сессию
                //отправить мыло
                //авторизовать юзера?
                request.setAttribute(MESSAGE, PASSWORD_UPDATED);
                router.setCurrentPage(PASSWORD_RECOVERY);

            }else {
                logger.log(Level.INFO, "UpdatePasswordCommand incorrect input data");
                request.setAttribute(MESSAGE, PASSWORD_NOT_UPDATED);
                router.setCurrentPage(PASSWORD_RECOVERY);
                for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }

            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "UpdatePasswordCommand command error. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
//            TODO !!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        return router;
        }
    }
