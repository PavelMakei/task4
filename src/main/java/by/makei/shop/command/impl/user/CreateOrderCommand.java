package by.makei.shop.command.impl.user;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.RedirectMessage;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
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

import static by.makei.shop.command.PagePath.*;

public class CreateOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(AttributeName.USER);
        Cart currentCart = (Cart) session.getAttribute(AttributeName.SESS_CART);
        BigDecimal commonOrderPrice = currentCart.getTotalCost();
        //if cart is empty
        if (currentCart.getTotalQuantity() == 0) {
            logger.log(Level.ERROR, "CreateOrderCommand empty cart. TotalCost = '{}', and userId ='{}' amount = '{}'"
                    , commonOrderPrice.doubleValue(), currentUser.getId(), currentUser.getAmount().doubleValue());
            setErrorPage("CreateOrderCommand empty cart", request, router);
            return router;
        }
        //if user has not enough money
        if (commonOrderPrice.compareTo(currentUser.getAmount()) > 0) {
            logger.log(Level.ERROR, "CreateOrderCommand not enough money to transaction. TotalCost = '{}', and userId ='{}' amount = '{}'"
                    , commonOrderPrice.doubleValue(), currentUser.getId(), currentUser.getAmount().doubleValue());
            setErrorPage("CreateOrderCommand not enough money to make transaction", request, router);
            return router;
        }
        //get params: address, detail, phone
        Map<String, String> orderDataMap = new HashMap<>();
        orderDataMap.put(AttributeName.PHONE, request.getParameter(AttributeName.PHONE));
        orderDataMap.put(AttributeName.ADDRESS, request.getParameter(AttributeName.ADDRESS));
        orderDataMap.put(AttributeName.DETAIL, request.getParameter(AttributeName.DETAIL));
        //validate and make transaction
        try {
            if (userService.createOrder(currentUser, currentCart, orderDataMap)) {
                router.setRedirectType();
                currentCart.clear();
                //find new user and set it to session
                Optional<User> optionalUser;
                optionalUser = userService.findUserByOneParam(AttributeName.ID, String.valueOf(currentUser.getId()));
                optionalUser.ifPresentOrElse(user -> {
                            session.setAttribute(AttributeName.USER, user);
                            router.setCurrentPage(GO_TO_MAIN + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.SUCCESSFULLY_ORDERED);
                        }
                        , () -> setErrorPage("user wasn't found by Id", request, router));
            } else {
                //return incorrect data
                logger.log(Level.INFO, "incorrect input data. Cancel operation");
                for (Map.Entry<String, String> entry : orderDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                router.setCurrentPage(CHECKOUT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "CreateOrderCommand command error. {}", e.getMessage());
            throw new CommandException("CreateOrderCommand command error", e);
        }
        return router;
    }

    private void setErrorPage(String message, HttpServletRequest request, Router router) {
        request.setAttribute(AttributeName.ERROR_MESSAGE, message);
        router.setCurrentPage(ERROR500);
    }
}
