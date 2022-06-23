package by.makei.shop.command.impl.user;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.service.mail.MailSender;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import by.makei.shop.util.CodeGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.command.PagePath.PASSWORD_RECOVERY;

public class RecoverySendActivationCode implements Command {
    private static final String CODE_SENT = "activation.code.sent";
    private static final String INCORRECT_EMAIL = "incorrect.email";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(AttributeName.EMAIL, request.getParameter(AttributeName.EMAIL));
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        String activationCode;
        try {
            if (parameterValidator.validateAndMarkUserData(userDataMap)
                && userService.findUserByEmail(userDataMap.get(AttributeName.EMAIL)).isPresent()) {
                activationCode = CodeGenerator.generateCode();
                session.setAttribute(AttributeName.SESS_EMAIL, userDataMap.get(AttributeName.EMAIL));
                session.setAttribute(AttributeName.SESS_ACTIVATION_CODE, activationCode);
                MailSender.sendActivationCodeByEmail(request, activationCode);
                request.setAttribute(AttributeName.MESSAGE, CODE_SENT);
                router.setCurrentPage(PASSWORD_RECOVERY);
                for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            } else {
                logger.log(Level.INFO, "RecoverySendActivationCode incorrect input data error");
                for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.setAttribute(AttributeName.MESSAGE, INCORRECT_EMAIL);
                router.setCurrentPage(PASSWORD_RECOVERY);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "RecoverySendActivationCode command error. {}", e.getMessage());
            throw new CommandException("RecoverySendActivationCode command error", e);
        }
        return router;
    }


}
