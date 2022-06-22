package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.mail.MailSender;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import by.makei.shop.util.CodeGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;
import static by.makei.shop.model.command.PagePath.REGISTRATION;

public class RegistrationSendActivationCodeCommand implements Command {
    private static final String CODE_SENT = "activation.code.sent";
    private static final String INCORRECT_EMAIL = "incorrect.email";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userDataMap.put(LAST_NAME, request.getParameter(LAST_NAME));
        userDataMap.put(LOGIN, request.getParameter(LOGIN));
        userDataMap.put(EMAIL, request.getParameter(EMAIL));
        userDataMap.put(PHONE, request.getParameter(PHONE));
        userDataMap.put(PASSWORD, request.getParameter(PASSWORD));
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        String activationCode;
        try {
            if (parameterValidator.validateAndMarkUserData(userDataMap)
                & parameterValidator.validateAndMarkIfLoginCorrectAndNotExistsInDb(userDataMap)
                & parameterValidator.validateAndMarkIfPhoneCorrectAndNotExistsInDb(userDataMap)
                & parameterValidator.validateAndMarkIfEmailCorrectAndNotExistsInDb(userDataMap)
            ) {
                activationCode = CodeGenerator.generateCode();
                session.setAttribute(SESS_EMAIL, userDataMap.get(EMAIL));
                session.setAttribute(SESS_ACTIVATION_CODE, activationCode);
                MailSender.sendActivationCodeByEmail(request, activationCode);
                request.setAttribute(MESSAGE, CODE_SENT);
                router.setCurrentPage(REGISTRATION);
                for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            } else {
                logger.log(Level.INFO,"Incorrect data input");
                for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.setAttribute(MESSAGE, INCORRECT_EMAIL);
                router.setCurrentPage(REGISTRATION);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "RegistrationSendActivationCodeCommand command error. {}", e.getMessage());
            throw new CommandException("RegistrationSendActivationCodeCommand command error",e);
        }
        return router;
    }

}
