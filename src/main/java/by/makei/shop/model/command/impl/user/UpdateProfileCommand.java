package by.makei.shop.model.command.impl.user;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.validator.ParameterValidator;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.*;
import static by.makei.shop.model.command.RedirectMessage.*;

public class UpdateProfileCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Router router = new Router();
        HttpSession session = request.getSession();
        Optional<User> optionalUser;
        boolean isCorrect = true;

        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userDataMap.put(LAST_NAME, request.getParameter(LAST_NAME));
        userDataMap.put(LOGIN, request.getParameter(LOGIN));
        userDataMap.put(EMAIL, request.getParameter(EMAIL));
        userDataMap.put(PHONE, request.getParameter(PHONE));
        try {
            if (parameterValidator.validateAndMarkUserData(userDataMap)) {
                User user = (User) session.getAttribute(USER);
                String currentUserid = String.valueOf(user.getId());
                //not for and switch because only 3params and add to the same map
                optionalUser = userService.findUserByOneParam(LOGIN, userDataMap.get(LOGIN));
                if (optionalUser.isPresent() && (optionalUser.get().getId() != Integer.parseInt(currentUserid))) {
                    logger.log(Level.ERROR, "UpdateProfileCommand busy login");
                    isCorrect = false;
                    userDataMap.put(BUSY_LOGIN, BUSY_LOGIN);
                }
                optionalUser = userService.findUserByOneParam(EMAIL, userDataMap.get(EMAIL));
                if (optionalUser.isPresent() && (optionalUser.get().getId() != Integer.parseInt(currentUserid))) {
                    logger.log(Level.ERROR, "UpdateProfileCommand busy email");
                    isCorrect = false;
                    userDataMap.put(BUSY_EMAIL, BUSY_EMAIL);
                }
                optionalUser = userService.findUserByOneParam(PHONE, userDataMap.get(PHONE));
                if (optionalUser.isPresent() && (optionalUser.get().getId() != Integer.parseInt(currentUserid))) {
                    logger.log(Level.ERROR, "UpdateProfileCommand busy phone");
                    isCorrect = false;
                    userDataMap.put(BUSY_PHONE, BUSY_PHONE);
                }
                if (isCorrect) {
                    userDataMap.put(ID, currentUserid);
                    //валидацию через код и email?
                    //проверять действующий пароль?
                    userService.updateProfile(userDataMap);
                    //если вернулся фолс?

                    optionalUser = userService.findUserByEmail(userDataMap.get(EMAIL));
                    if (optionalUser.isPresent()) {
                        session.setAttribute(USER, optionalUser.get());
                        router.setRedirectType();
                        router.setCurrentPage(GO_TO_MAIN + REDIRECT_MESSAGE + UPDATE_SUCCESS);

                    } else {
                        logger.log(Level.ERROR, "UpdateProfile command user wasn't found after update profile");
                        request.setAttribute(ERROR_MESSAGE, "UpdateProfile command user wasn't found after update profile");
                        router.setCurrentPage(ERROR500);
                        return router;
                    }
                } else {
                    logger.log(Level.INFO, "UpdateProfileCommand incorrect params was given");
                    for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                        router.setCurrentPage(UPDATE_PROFILE);
                        request.setAttribute(MESSAGE, UPDATE_FAIL);
                    }
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "UpdateProfileCommand command error. {}", e.getMessage());
            throw new CommandException("UpdateProfileCommand command error",e);
        }
        return router;
    }
}
