package by.makei.shop.command.impl.user;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.RedirectMessage;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
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

import static by.makei.shop.command.PagePath.*;

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
        userDataMap.put(AttributeName.FIRST_NAME, request.getParameter(AttributeName.FIRST_NAME));
        userDataMap.put(AttributeName.LAST_NAME, request.getParameter(AttributeName.LAST_NAME));
        userDataMap.put(AttributeName.LOGIN, request.getParameter(AttributeName.LOGIN));
        userDataMap.put(AttributeName.EMAIL, request.getParameter(AttributeName.EMAIL));
        userDataMap.put(AttributeName.PHONE, request.getParameter(AttributeName.PHONE));
        try {
            if (parameterValidator.validateAndMarkIncomeData(userDataMap)) {
                User user = (User) session.getAttribute(AttributeName.USER);
                String currentUserid = String.valueOf(user.getId());
                //not for and switch because only 3params and add to the same map
                optionalUser = userService.findUserByOneParam(AttributeName.LOGIN, userDataMap.get(AttributeName.LOGIN));
                if (optionalUser.isPresent() && (optionalUser.get().getId() != Integer.parseInt(currentUserid))) {
                    logger.log(Level.ERROR, "UpdateProfileCommand busy login");
                    isCorrect = false;
                    userDataMap.put(AttributeName.BUSY_LOGIN, AttributeName.BUSY_LOGIN);
                }
                optionalUser = userService.findUserByOneParam(AttributeName.EMAIL, userDataMap.get(AttributeName.EMAIL));
                if (optionalUser.isPresent() && (optionalUser.get().getId() != Integer.parseInt(currentUserid))) {
                    logger.log(Level.ERROR, "UpdateProfileCommand busy email");
                    isCorrect = false;
                    userDataMap.put(AttributeName.BUSY_EMAIL, AttributeName.BUSY_EMAIL);
                }
                optionalUser = userService.findUserByOneParam(AttributeName.PHONE, userDataMap.get(AttributeName.PHONE));
                if (optionalUser.isPresent() && (optionalUser.get().getId() != Integer.parseInt(currentUserid))) {
                    logger.log(Level.ERROR, "UpdateProfileCommand busy phone");
                    isCorrect = false;
                    userDataMap.put(AttributeName.BUSY_PHONE, AttributeName.BUSY_PHONE);
                }
                if (isCorrect) {
                    userDataMap.put(AttributeName.ID, currentUserid);
                    //validate though email?
                    //check current password?
                    userService.updateProfile(userDataMap);
                    //check if return false?
                    optionalUser = userService.findUserByEmail(userDataMap.get(AttributeName.EMAIL));
                    if (optionalUser.isPresent()) {
                        session.setAttribute(AttributeName.USER, optionalUser.get());
                        router.setRedirectType();
                        router.setCurrentPage(GO_TO_MAIN + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.UPDATE_SUCCESS);

                    } else {
                        logger.log(Level.ERROR, "UpdateProfile command user wasn't found after update profile");
                        request.setAttribute(AttributeName.ERROR_MESSAGE, "UpdateProfile command user wasn't found after update profile");
                        router.setCurrentPage(ERROR500);
                        return router;
                    }
                } else {
                    logger.log(Level.INFO, "UpdateProfileCommand incorrect params was given");
                    for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                        router.setCurrentPage(UPDATE_PROFILE);
                        request.setAttribute(AttributeName.MESSAGE, RedirectMessage.UPDATE_FAIL);
                    }
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "UpdateProfileCommand command error. {}", e.getMessage());
            throw new CommandException("UpdateProfileCommand command error", e);
        }
        return router;
    }
}
