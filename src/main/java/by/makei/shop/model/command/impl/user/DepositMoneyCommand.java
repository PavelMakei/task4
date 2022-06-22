package by.makei.shop.model.command.impl.user;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.impl.UserServiceImpl;
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
    private static final String USER_NOT_FOUND = "DepositMoneyCommand user wasn't found after update amount";
    private static final String UNKNOWN_ERROR = "DepositMoneyCommand unknown error";


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
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
            if (!userService.validateAndMarkIncomeData(depositDataMap)) {
                logger.log(Level.INFO, "DepositMoneyCommand incorrect input data. Operation canceled");
                for (Map.Entry<String, String> entry : depositDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                    router.setCurrentPage(DEPOSIT_MONEY);
                    request.setAttribute(MESSAGE, USER_MONEY_AMOUNT_UPDATING_FAIL);
                }
            }
            currentUser = (User) session.getAttribute(USER);
            currentUserId = currentUser.getId();
            currentUserAmount = currentUser.getAmount(); //or get fresh user data from DB?
            if (!userService.updateUserMoneyAmount(currentUserId, currentUserAmount, depositDataMap.get(AMOUNT_TO_DEPOSIT))) {
                logger.log(Level.ERROR,"unknown error");
                return goToError(UNKNOWN_ERROR, request, router);
            }
            optionalUser = userService.findUserByOneParam(ID, String.valueOf(currentUserId));
            if (optionalUser.isPresent()) {
                currentUser = optionalUser.get();
                session.setAttribute(USER, currentUser);
                router.setRedirectType();
                router.setCurrentPage(GO_TO_MAIN + REDIRECT_MESSAGE + USER_MONEY_AMOUNT_UPDATED);
            } else {
                logger.log(Level.WARN,"user wasn't found");
                return goToError(USER_NOT_FOUND, request, router);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "DepositMoneyCommand command error. {}", e.getMessage());
            throw new CommandException("DepositMoneyCommand command error",e);
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
