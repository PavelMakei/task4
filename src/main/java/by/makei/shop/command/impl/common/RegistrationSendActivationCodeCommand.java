package by.makei.shop.command.impl.common;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.service.mail.MailSender;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import by.makei.shop.util.CodeGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.command.PagePath.REGISTRATION;

public class RegistrationSendActivationCodeCommand implements Command {
    private static final String CODE_SENT = "activation.code.sent";
    private static final String INCORRECT_EMAIL = "incorrect.email";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(AttributeName.FIRST_NAME, request.getParameter(AttributeName.FIRST_NAME));
        userDataMap.put(AttributeName.LAST_NAME, request.getParameter(AttributeName.LAST_NAME));
        userDataMap.put(AttributeName.LOGIN, request.getParameter(AttributeName.LOGIN));
        userDataMap.put(AttributeName.EMAIL, request.getParameter(AttributeName.EMAIL));
        userDataMap.put(AttributeName.PHONE, request.getParameter(AttributeName.PHONE));
        userDataMap.put(AttributeName.PASSWORD, request.getParameter(AttributeName.PASSWORD));
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        String activationCode;
        try {
            if (parameterValidator.validateAndMarkUserData(userDataMap)
                & parameterValidator.validateAndMarkIfLoginCorrectAndNotExistsInDb(userDataMap)
                & parameterValidator.validateAndMarkIfPhoneCorrectAndNotExistsInDb(userDataMap)
                & parameterValidator.validateAndMarkIfEmailCorrectAndNotExistsInDb(userDataMap)
            ) {
                activationCode = CodeGenerator.generateCode();
                session.setAttribute(AttributeName.SESS_EMAIL, userDataMap.get(AttributeName.EMAIL));
                session.setAttribute(AttributeName.SESS_ACTIVATION_CODE, activationCode);
                MailSender.sendActivationCodeByEmail(request, activationCode);
                request.setAttribute(AttributeName.MESSAGE, CODE_SENT);
                router.setCurrentPage(REGISTRATION);
                for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            } else {
                logger.log(Level.INFO, "Incorrect data input");
                for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.setAttribute(AttributeName.MESSAGE, INCORRECT_EMAIL);
                router.setCurrentPage(REGISTRATION);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "RegistrationSendActivationCodeCommand command error. {}", e.getMessage());
            throw new CommandException("RegistrationSendActivationCodeCommand command error", e);
        }
        return router;
    }

}
