package by.makei.shop.model.command.impl.user;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.Cart;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.model.validator.ParameterValidator;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.GO_TO_MAIN;
import static by.makei.shop.model.command.RedirectMessage.REDIRECT_MESSAGE;

public class CreateOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String message;
        Router router = new Router();
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(USER);
        Cart currentCart = (Cart) session.getAttribute(SESS_CART);
        BigDecimal commonOrderPrice = currentCart.getTotalCost();
        //проверить не пуста ли корзина
        if(currentCart.getProductQuantity().equals(0)){
            logger.log(Level.ERROR, "CreateOrderCommand empty cart. TotalCost = '{}', and userId ='{}' amount = '{}'"
                    , commonOrderPrice.doubleValue(), currentUser.getId(), currentUser.getAmount().doubleValue());
            throwNewException("CreateOrderCommand empty cart");

        }
        //проверить достаточно ли денег
        if (commonOrderPrice.compareTo(currentUser.getAmount()) > 0) {
            logger.log(Level.ERROR, "CreateOrderCommand not enough money to transaction. TotalCost = '{}', and userId ='{}' amount = '{}'"
                    , commonOrderPrice.doubleValue(), currentUser.getId(), currentUser.getAmount().doubleValue());
            throwNewException("CreateOrderCommand not enough money to make transaction");
        }


        Map<String, String> orderDataMap = new HashMap<>();
        //принять данные address, description, phone
        orderDataMap.put(PHONE, request.getParameter(PHONE));
        orderDataMap.put(ADDRESS, request.getParameter(ADDRESS));
        orderDataMap.put(DESCRIPTION, request.getParameter(DESCRIPTION));
        //провалидировать в сервисе
        //провести транзакцию
if(userService.createOrder(currentUser, currentCart, orderDataMap)){
    router.setRedirectType();
    currentCart.clear();    //Очистить корзину
    //получить нового пользователя из базы? или изменить амаунт у существующего?
    //Посетать нового пользователя?
    router.setCurrentPage(GO_TO_MAIN+REDIRECT_MESSAGE+SUCCESSFULLY_ORDERED);
}else{
    //некорректные отправить назад

}

        return router;
    }

    private void throwNewException(String message) {

    }
}
