package by.makei.shop.model.command.impl.user;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.Cart;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
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
import static by.makei.shop.model.command.RedirectMessage.REDIRECT_MESSAGE;
import static by.makei.shop.model.command.RedirectMessage.SUCCESSFULLY_ORDERED;

public class CreateOrderCommand implements Command {
    private static final String ERROR = "CreateOrderCommand Service exception : ";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(USER);
        Cart currentCart = (Cart) session.getAttribute(SESS_CART);
        BigDecimal commonOrderPrice = currentCart.getTotalCost();
        //проверить не пуста ли корзина
        if (currentCart.getProductQuantity().equals(0)) {
            logger.log(Level.ERROR, "CreateOrderCommand empty cart. TotalCost = '{}', and userId ='{}' amount = '{}'"
                    , commonOrderPrice.doubleValue(), currentUser.getId(), currentUser.getAmount().doubleValue());
            throwNewException("CreateOrderCommand empty cart", request, router);
            return router;
        }
        //проверить достаточно ли денег
        if (commonOrderPrice.compareTo(currentUser.getAmount()) > 0) {
            logger.log(Level.ERROR, "CreateOrderCommand not enough money to transaction. TotalCost = '{}', and userId ='{}' amount = '{}'"
                    , commonOrderPrice.doubleValue(), currentUser.getId(), currentUser.getAmount().doubleValue());
            throwNewException("CreateOrderCommand not enough money to make transaction", request, router);
            return router;
        }
        //принять данные address, description, phone
        Map<String, String> orderDataMap = new HashMap<>();
        orderDataMap.put(PHONE, request.getParameter(PHONE));
        orderDataMap.put(ADDRESS, request.getParameter(ADDRESS));
        orderDataMap.put(DESCRIPTION, request.getParameter(DESCRIPTION));
        //провалидировать в сервисе
        //провести транзакцию
        try {
            if (userService.createOrder(currentUser, currentCart, orderDataMap)) {
                router.setRedirectType();
                currentCart.clear();    //Очистить корзину
                //получить нового пользователя из базы!!! и посетать в сессию
                Optional<User> optionalUser;
                optionalUser = userService.findUserByOneParam(ID, String.valueOf(currentUser.getId()));
                optionalUser.ifPresentOrElse(user -> {
                            session.setAttribute(USER, user);
                            router.setCurrentPage(GO_TO_MAIN + REDIRECT_MESSAGE + SUCCESSFULLY_ORDERED);
                        }
                        , () -> throwNewException("user wasn't found by Id", request, router));
            } else {
                //некорректные отправить назад
                for (Map.Entry<String, String> entry : orderDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                router.setCurrentPage(CHECKOUT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR,"Service exception: {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e);
            router.setCurrentPage(ERROR500);
        }
        return router;
}

    private void throwNewException(String message, HttpServletRequest request, Router router) {
        request.setAttribute(ERROR_MESSAGE, message);
        router.setCurrentPage(ERROR500);
    }
}
