package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.PagePath;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.service.mail.MailService;
import by.makei.shop.model.validator.ParameterValidator;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import by.makei.shop.util.ResourceManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;

public class RegistrationCommand implements Command {
    private static final String LOCALE_SPLIT_REGEXP = "_";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Router router = new Router();
        HttpSession session = request.getSession();

        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userDataMap.put(LAST_NAME, request.getParameter(LAST_NAME));
        userDataMap.put(LOGIN, request.getParameter(LOGIN));
        userDataMap.put(EMAIL, request.getParameter(EMAIL));
        userDataMap.put(PHONE, request.getParameter(PHONE));
        userDataMap.put(PASSWORD, request.getParameter(PASSWORD));
        userDataMap.put(ACTIVATION_CODE, request.getParameter(ACTIVATION_CODE));

        try {

        if (parameterValidator.validateUserData(userDataMap)&parameterValidator.validateActivationCodeAndSavedEmail(userDataMap,session)) {
            userService.createUser(userDataMap);
            Optional<User> optionalUser = userService.findUserByEmail(userDataMap.get(EMAIL));
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                session.setAttribute(ACCESS_LEVEL, user.getAccessLevel());
            } else {
                logger.log(Level.WARN, "RegistrationCommand. Can't be reached. User wasn't found in DB");
                request.setAttribute(ERROR_MESSAGE, "RegistrationCommand. User wasn't found in DB");
                router.setCurrentPage(ERROR500);
                return router;
            }
            router.setCurrentPage(PagePath.INDEX);
            sendRegistrationEmail(request);
            session.removeAttribute(SESS_ACTIVATION_CODE);
            session.removeAttribute(SESS_EMAIL);
        } else {
            //если невалидно, записываем станрые значения и проблемы в реквест, возвращаемся на страницу регистрации
            for(Map.Entry<String,String> entry: userDataMap.entrySet()){
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            router.setCurrentPage(PagePath.REGISTRATION);
        }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "registration command error. {}", e.getMessage());
            throw new CommandException("registration command error.",e);
        }
        return router;
    }
    private void sendRegistrationEmail(HttpServletRequest request) {
        ResourceManager manager = ResourceManager.INSTANCE;
        String locale = request.getSession().getAttribute(LOCALE).toString();
        String[] languageAndCountry = locale.split(LOCALE_SPLIT_REGEXP);
        manager.changeResource(new Locale(languageAndCountry[0],languageAndCountry[1]));

        String login = request.getParameter(LOGIN);
        String password  = request.getParameter(PASSWORD);
        String sendEmailTo = request.getParameter(EMAIL);
        StringBuilder mailText = new StringBuilder("");

        //TODO вынести литералы ? Смысл?
        mailText.append(manager.getString("lightingshop.registration.successful"))
                .append("\n")
                .append(manager.getString("your.login.is"))
                .append(" :")
                .append(login)
                .append("\n")
                .append(manager.getString("your.password.is"))
                .append(" :")
                .append(password)
        .append("\n")
                .append(manager.getString("registration.welcome"));

        String mailSubject = manager.getString("registration.mail.subject");

        MailService mailService = new MailService();
        mailService.sendEmail(sendEmailTo,mailSubject,mailText.toString());
    }
}
