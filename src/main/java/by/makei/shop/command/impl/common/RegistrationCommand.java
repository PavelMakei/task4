package by.makei.shop.command.impl.common;

import by.makei.shop.command.*;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.service.mail.MailService;
import by.makei.shop.util.ResourceManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static by.makei.shop.command.PagePath.ERROR500;
import static by.makei.shop.command.PagePath.GO_TO_MAIN;

public class RegistrationCommand implements Command {
    private static final String LOCALE_SPLIT_REGEXP = "_";
    private static final String MESSAGE_YOUR_PASSWORD_IS = "your.password.is";
    private static final String MESSAGE_YOUR_LOGIN_IS = "your.login.is";
    private static final String MESSAGE_SHOP_REGISTRATION_SUCCESSFUL = "lightingshop.registration.successful";
    private static final String MESSAGE_SHOP_WELCOME = "registration.welcome";
    private static final String MAIL_SUBJECT = "registration.mail.subject";


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Router router = new Router();
        HttpSession session = request.getSession();

        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(AttributeName.FIRST_NAME, request.getParameter(AttributeName.FIRST_NAME));
        userDataMap.put(AttributeName.LAST_NAME, request.getParameter(AttributeName.LAST_NAME));
        userDataMap.put(AttributeName.LOGIN, request.getParameter(AttributeName.LOGIN));
        userDataMap.put(AttributeName.EMAIL, request.getParameter(AttributeName.EMAIL));
        userDataMap.put(AttributeName.PHONE, request.getParameter(AttributeName.PHONE));
        userDataMap.put(AttributeName.PASSWORD, request.getParameter(AttributeName.PASSWORD));
        userDataMap.put(AttributeName.ACTIVATION_CODE, request.getParameter(AttributeName.ACTIVATION_CODE));
        try {
            if (userService.createUser(userDataMap, session)) {
                Optional<User> optionalUser = userService.findUserByEmail(userDataMap.get(AttributeName.EMAIL));
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    session.setAttribute(AttributeName.USER, user);
                    session.setAttribute(AttributeName.ACCESS_LEVEL, user.getAccessLevel());
                } else {
                    logger.log(Level.WARN, "RegistrationCommand. Can't be reached. User wasn't found in DB");
                    request.setAttribute(AttributeName.ERROR_MESSAGE, "RegistrationCommand. User wasn't found in DB");
                    router.setCurrentPage(ERROR500);
                    return router;
                }
                router.setCurrentPage(GO_TO_MAIN + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.USER_WELCOME);
                router.setRedirectType();
                sendRegistrationEmail(request);
                session.removeAttribute(AttributeName.SESS_ACTIVATION_CODE);
                session.removeAttribute(AttributeName.SESS_EMAIL);
            } else {
                //if data is incorrect, return it to page
                for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                router.setCurrentPage(PagePath.REGISTRATION);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "registration command error. {}", e.getMessage());
            throw new CommandException("registration command error.", e);
        }
        return router;
    }

    private void sendRegistrationEmail(HttpServletRequest request) {
        ResourceManager manager = ResourceManager.INSTANCE;
        String locale = request.getSession().getAttribute(AttributeName.LOCALE).toString();
        String[] languageAndCountry = locale.split(LOCALE_SPLIT_REGEXP);
        manager.changeResource(new Locale(languageAndCountry[0], languageAndCountry[1]));
        String login = request.getParameter(AttributeName.LOGIN);
        String password = request.getParameter(AttributeName.PASSWORD);
        String sendEmailTo = request.getParameter(AttributeName.EMAIL);
        StringBuilder mailText = new StringBuilder("");
        // Add other thread for mail (Blinov's book)?
        mailText.append(manager.getString(MESSAGE_SHOP_REGISTRATION_SUCCESSFUL))
                .append("\n")
                .append(manager.getString(MESSAGE_YOUR_LOGIN_IS))
                .append(" :")
                .append(login)
                .append("\n")
                .append(manager.getString(MESSAGE_YOUR_PASSWORD_IS))
                .append(" :")
                .append(password)
                .append("\n")
                .append(manager.getString(MESSAGE_SHOP_WELCOME));
        String mailSubject = manager.getString(MAIL_SUBJECT);
        MailService mailService = new MailService();
        mailService.sendEmail(sendEmailTo, mailSubject, mailText.toString());
    }
}
