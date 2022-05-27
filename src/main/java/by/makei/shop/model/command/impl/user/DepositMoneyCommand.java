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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.*;
import static by.makei.shop.model.command.RedirectMessage.*;

public class DepositMoneyCommand implements Command {
    private static final String ERROR = "DepositMoneyCommand Service exception : ";
    private static final String USER_NOT_FOUND = "DepositMoneyCommand user wasn't found after update amount";
    private static final String UNKNOWN_ERROR = "DepositMoneyCommand unknown error";


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Router router = new Router();
        HttpSession session = request.getSession();
        Optional<User> optionalUser;
        User currentUser;
        int currentUserId;
        BigDecimal currentUserAmount;

        Map<String, String> depositDataMap = new HashMap();
        depositDataMap.put(CARD_NUMBER, request.getParameter(CARD_NUMBER));
        depositDataMap.put(CARD_EXP_DATE, request.getParameter(CARD_EXP_DATE));
        depositDataMap.put(CARD_CVC, request.getParameter(CARD_CVC));
        depositDataMap.put(CARD_HOLDER, request.getParameter(CARD_HOLDER));
        depositDataMap.put(AMOUNT_TO_DEPOSIT, request.getParameter(AMOUNT_TO_DEPOSIT));
        try {
            //проверить данные
            if (parameterValidator.validateAndMarkDepositData(depositDataMap)) {
//достать юзера из сессии
                currentUser = (User) session.getAttribute(USER);
//достать ID юзера
                currentUserId = currentUser.getId();
                //достать баланс у юзера
                currentUserAmount = currentUser.getAmount(); //or get fresh user data from DB?
                //отправить полученную сумму, баланс и айди в сервис. В сервисе суммировать
                if (!userService.updateUserMoneyAmount(currentUserId, currentUserAmount, depositDataMap.get(AMOUNT_TO_DEPOSIT))) {
                    return goToError(UNKNOWN_ERROR, request, router);
                }
                //из сервиса вернуть нового юзера из БД по айди
                optionalUser = userService.findUserByOneParam(ID, String.valueOf(currentUserId));
                if (optionalUser.isPresent()) {
                    currentUser = optionalUser.get();
                    //вытащить текущий баланс
                    currentUserAmount = currentUser.getAmount();
                    //записать в сессию новые данные юзера
                    session.setAttribute(USER, currentUser);
                    //Вернуть на главную с сообщением
                    router.setRedirectType();
                    router.setCurrentPage(GO_TO_MAIN + REDIRECT_MESSAGE + USER_MONEY_AMOUNT_UPDATED);
                } else {
                    return goToError(USER_NOT_FOUND, request, router);
                }
            } else {
                logger.log(Level.INFO, "DepositMoneyCommand incorrect params was given");
                for (Map.Entry<String, String> entry : depositDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                    router.setCurrentPage(DEPOSIT_MONEY);
                    request.setAttribute(MESSAGE, USER_MONEY_AMOUNT_UPDATING_FAIL);
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "DepositMoneyCommand. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e);
            router.setCurrentPage(ERROR500);
        }
        return router;
    }

    private Router goToError(String message, HttpServletRequest request, Router router) {
        logger.log(Level.ERROR, message);
        request.setAttribute(ERROR_MESSAGE, message);
        router.setCurrentPage(ERROR500);
        return router;
    }
}
