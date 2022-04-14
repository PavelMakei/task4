package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.PagePath;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.validator.AttributeValidator;
import by.makei.shop.model.validator.impl.AttributeValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public Router execute(HttpServletRequest request) {
        AttributeValidator validator = AttributeValidatorImpl.getInstance();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Router router = new Router();

//        String userFirstName = request.getParameter(FIRST_NAME);
//        String userLastName = request.getParameter(LAST_NAME);
//        String userLogin = request.getParameter(LOGIN);
//        String userEmail = request.getParameter(EMAIL);
//        String userPhone = request.getParameter(PHONE);
//        String userPassword = request.getParameter(PASSWORD);

        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userDataMap.put(LAST_NAME, request.getParameter(LAST_NAME));
        userDataMap.put(LOGIN, request.getParameter(LOGIN));
        userDataMap.put(EMAIL, request.getParameter(EMAIL));
        userDataMap.put(PHONE, request.getParameter(PHONE));
        userDataMap.put(PASSWORD, request.getParameter(PASSWORD));

//        boolean isValid = true;

//
//        if(!validator.isNameValid(userFirstName)){
//            //TODO add wrong value message?
//            isValid = false;
//        }
//        if(!validator.isNameValid(userLastName)){
//            isValid = false;
//        }
//        if(!validator.isLoginValid(userLogin)){
//            isValid = false;
//        }
//        if(!validator.isEmailValid(userEmail)){
//            isValid = false;
//        }
//        if(!validator.isPhoneValid(userPhone)){
//            isValid = false;
//        }
//        if(!validator.isPasswordValid(userPassword)){
//            isValid = false;
//        }

        //TODO проверку на наличие такого пользователя по логину, паролю, телефону, мылу?

        if (userService.validateUserData(userDataMap)) {
            // если валидно, то добавляем юзера в БД и переходим в мэин?
            try {
                userService.addNewUser(userDataMap);
            } catch (ServiceException e) {
                //TODO!!!!
                e.printStackTrace();
            }
            //TODO переход по редирект и/или на страницу с уведомлением?
            router.setCurrentPage(PagePath.TEMP);
            //TODO only for test - remove!!!

            return router;
        } else {
            //если невалидно, записываем станрые значения и проблемы в реквест, возвращаемся на страницу регистрации
            for(Map.Entry<String,String> entry: userDataMap.entrySet()){
                request.setAttribute(entry.getKey(), entry.getValue());
            }
//            for (Map.Entry<String, String> entry : userDataMap.entrySet()) {
//                String key = entry.getKey();
//                switch (key) {
//                    case FIRST_NAME -> {
//                        if (entry.getValue().equals(INVALID_FIRST_NAME)) {
//                            request.setAttribute(INVALID_FIRST_NAME, INVALID_FIRST_NAME);
//                        }
//                        request.setAttribute(FIRST_NAME,entry.getValue());
//                    }
//                    case LAST_NAME -> {
//                        if (entry.getValue().equals(INVALID_LAST_NAME)) {
//                            request.setAttribute(INVALID_LAST_NAME, INVALID_LAST_NAME);
//                        }
//                        request.setAttribute(LAST_NAME,entry.getValue());
//                    }
//                    case LOGIN -> {
//                        if (entry.getValue().equals(INVALID_LOGIN)) {
//                            request.setAttribute(INVALID_LOGIN, INVALID_LOGIN);
//                            //TODO if busy?}
//                        }
//                        request.setAttribute(LOGIN,entry.getValue());
//                    }
//                    case EMAIL -> {
//                        if (entry.getValue().equals(INVALID_EMAIL)) {
//                            request.setAttribute(INVALID_EMAIL, INVALID_EMAIL);
//                            //TODO if busy
//                        }
//                        request.setAttribute(EMAIL,entry.getValue());
//                    }
//                    case PHONE -> {
//                        if (entry.getValue().equals(INVALID_PHONE)) {
//                            request.setAttribute(INVALID_PHONE, INVALID_PHONE);
//                            //TODO if busy
//                        }
//                        request.setAttribute(PHONE,entry.getValue());
//                    }
//                    case PASSWORD -> {
//                        if (entry.getValue().equals(INVALID_PASSWORD)) {
//                            request.setAttribute(INVALID_PASSWORD, INVALID_PASSWORD);
//                        }
//                        // TODO if it need? request.setAttribute(PASSWORD,entry.getValue());
//                    }
//                }
//            }
            router.setCurrentPage(PagePath.REGISTRATION);
        }
        return router;
    }
}
