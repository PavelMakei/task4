package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
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
    public Router execute(HttpServletRequest request) throws CommandException {
        AttributeValidator validator = AttributeValidatorImpl.getInstance();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Router router = new Router();

        Map<String, String> userDataMap = new HashMap();
        userDataMap.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userDataMap.put(LAST_NAME, request.getParameter(LAST_NAME));
        userDataMap.put(LOGIN, request.getParameter(LOGIN));
        userDataMap.put(EMAIL, request.getParameter(EMAIL));
        userDataMap.put(PHONE, request.getParameter(PHONE));
        userDataMap.put(PASSWORD, request.getParameter(PASSWORD));


        try {
        //TODO проверку на наличие такого пользователя по логину, паролю, телефону, мылу?

        if (userService.validateUserData(userDataMap)) {
            // если валидно, то добавляем юзера в БД и переходим в мэин?

                userService.addNewUser(userDataMap);

            //TODO переход по редирект и/или на страницу с уведомлением?
            router.setCurrentPage(PagePath.TEMP);
            //TODO only for test - remove!!!

            return router;
        } else {
            //если невалидно, записываем станрые значения и проблемы в реквест, возвращаемся на страницу регистрации
            for(Map.Entry<String,String> entry: userDataMap.entrySet()){
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            router.setCurrentPage(PagePath.REGISTRATION);
        }
        } catch (ServiceException e) {
            //TODO!!!!
            throw new CommandException(e);
        }
        return router;
    }
}
