package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.service.mail.MailService;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import by.makei.shop.util.CodeGenerator;
import by.makei.shop.util.ResourceManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.REGISTRATION;

public class SendActivationCode implements Command {
    private static final String LOCALE_SPLIT_REGEXP = "_";
    private static final String CODE_SENT = "activation.code.sent";
    private static final String INCORRECT_EMAIL = "incorrect.email";


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
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
            if (parameterValidator.validateUserData(userDataMap)) {
                activationCode = CodeGenerator.generateCode();
                session.setAttribute(SESS_EMAIL, userDataMap.get(EMAIL));
                session.setAttribute(SESS_ACTIVATION_CODE, activationCode);
                sendCodeByEmail(request, activationCode);
                request.setAttribute(MESSAGE, CODE_SENT);
                router.setCurrentPage(REGISTRATION);
                for(Map.Entry<String,String> entry: userDataMap.entrySet()){
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            }else{
                for(Map.Entry<String,String> entry: userDataMap.entrySet()){
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.setAttribute(MESSAGE, INCORRECT_EMAIL);
                router.setCurrentPage(REGISTRATION);
            }
        } catch (ServiceException e){

            //TODO
        }

        //проверить мыло 1
            //записать мыло в сессию
            //сгенерить код
            //выслать код
            //вставить сообщение
            //вернуться на страницу


        return router;
    }

        private void sendCodeByEmail(HttpServletRequest request, String activationCode) {
            ResourceManager manager = ResourceManager.INSTANCE;
            String locale = request.getSession().getAttribute(LOCALE).toString();
            String[] languageAndCountry = locale.split(LOCALE_SPLIT_REGEXP);
            manager.changeResource(new Locale(languageAndCountry[0],languageAndCountry[1]));
            String sendEmailTo = request.getParameter(EMAIL);
            StringBuilder mailText = new StringBuilder("");

            mailText.append(manager.getString("lightingshop.activation.code"))
                    .append("\n")
                    .append(manager.getString("your.activation.code.is"))
                    .append(" :")
                    .append(activationCode);

            String mailSubject = manager.getString("lightingshop.activation.code");

            MailService mailService = new MailService();
            mailService.sendEmail(sendEmailTo,mailSubject,mailText.toString());
        }
}
