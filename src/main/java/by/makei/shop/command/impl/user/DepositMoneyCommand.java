package by.makei.shop.command.impl.user;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.RedirectMessage;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.makei.shop.command.PagePath.*;

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
        depositDataMap.put(AttributeName.CARD_NUMBER, request.getParameter(AttributeName.CARD_NUMBER));
        depositDataMap.put(AttributeName.CARD_EXP_DATE, request.getParameter(AttributeName.CARD_EXP_DATE));
        depositDataMap.put(AttributeName.CARD_CVC, request.getParameter(AttributeName.CARD_CVC));
        depositDataMap.put(AttributeName.CARD_HOLDER, request.getParameter(AttributeName.CARD_HOLDER));
        depositDataMap.put(AttributeName.AMOUNT_TO_DEPOSIT, request.getParameter(AttributeName.AMOUNT_TO_DEPOSIT));
        try {
            if (!userService.validateAndMarkIncomeData(depositDataMap)) {
                logger.log(Level.ERROR, "DepositMoneyCommand incorrect input data. Operation canceled");
                for (Map.Entry<String, String> entry : depositDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                    router.setCurrentPage(DEPOSIT_MONEY);
                    request.setAttribute(AttributeName.MESSAGE, RedirectMessage.USER_MONEY_AMOUNT_UPDATING_FAIL);
                }
                return router;
            }
            currentUser = (User) session.getAttribute(AttributeName.USER);
            currentUserId = currentUser.getId();
            currentUserAmount = currentUser.getAmount(); //or get fresh user data from DB?
            if (!userService.updateUserMoneyAmount(currentUserId, currentUserAmount, depositDataMap.get(AttributeName.AMOUNT_TO_DEPOSIT))) {
                logger.log(Level.ERROR, "unknown error");
                return goToError(UNKNOWN_ERROR, request, router);
            }
            optionalUser = userService.findUserByOneParam(AttributeName.ID, String.valueOf(currentUserId));
            if (optionalUser.isPresent()) {
                currentUser = optionalUser.get();
                session.setAttribute(AttributeName.USER, currentUser);
                router.setRedirectType();
                router.setCurrentPage(GO_TO_MAIN + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.USER_MONEY_AMOUNT_UPDATED);
            } else {
                logger.log(Level.WARN, "user wasn't found");
                return goToError(USER_NOT_FOUND, request, router);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "DepositMoneyCommand command error. {}", e.getMessage());
            throw new CommandException("DepositMoneyCommand command error", e);
        }
        return router;
    }

    private Router goToError(String message, HttpServletRequest request, Router router) {
        logger.log(Level.ERROR, message);
        request.setAttribute(AttributeName.ERROR_MESSAGE, message);
        router.setCurrentPage(ERROR500);
        return router;
    }
}
