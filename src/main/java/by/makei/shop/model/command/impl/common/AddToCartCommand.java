package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.Cart;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import by.makei.shop.model.validator.ParameterValidator;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.*;
import static by.makei.shop.model.command.RedirectMessage.*;

public class AddToCartCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        ProductService productService = ProductServiceImpl.getInstance();
        Cart cart;
        Map<String, String> inputProductIdQuantity = new HashMap<>();
        Map<Product, String> productQuantityMapFromDB = new HashMap<>();
        inputProductIdQuantity.put(ID, request.getParameter(ID));
        inputProductIdQuantity.put(QUANTITY, request.getParameter(QUANTITY));
        cart = (Cart) session.getAttribute(SESS_CART);
        int currentQuantity = 0;
        int inputQuantity = 0;
        int quantityInDb = 0;
        String message;
        Product product = null;

        try {
            if ((productService.findMapProductQuantityById(inputProductIdQuantity, productQuantityMapFromDB))
                && (inputQuantity = Integer.parseInt(inputProductIdQuantity.get(QUANTITY)))>0) {
                inputQuantity = Integer.parseInt(inputProductIdQuantity.get(QUANTITY));
                if (productQuantityMapFromDB.size() != 1) {
                    logger.log(Level.WARN, "AddToCartCommand error while find product in DB, quantity of product = {}", productQuantityMapFromDB.size());
                    request.setAttribute(ERROR_MESSAGE, "AddToCartCommand error while find product in DB, quantity of product = " + productQuantityMapFromDB.size());
                    router.setCurrentPage(ERROR500);
                    return router;
                }
                for (Map.Entry<Product, String> entry : productQuantityMapFromDB.entrySet()) {
                    product = entry.getKey();
                    quantityInDb = Integer.parseInt(entry.getValue());
                }
                if (cart.getProductQuantity().containsKey(product)) {
                    currentQuantity = cart.getProductQuantity().get(product);
                }
                currentQuantity += inputQuantity;
                if (currentQuantity > Cart.MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY) {
                    logger.log(Level.INFO, "AddToCartCommand asked product quantity more than " + Cart.MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY);
                    currentQuantity = Cart.MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY;
                    message = PRODUCT_MAXIMUM_REACHED;
                } else if (currentQuantity > quantityInDb) {
                    logger.log(Level.INFO, "AddToCartCommand asked product quantity more than in stock");
                    currentQuantity = quantityInDb;
                    message = NO_MORE_IN_STOCK;
                } else {
                    message = PRODUCT_ADDED_TO_CARD;
                }
                cart.putProductQuantity(product, currentQuantity);
                session.setAttribute(SESS_CART, cart);
                router.setRedirectType();
                router.setCurrentPage(GO_TO_SHOW_CART + REDIRECT_MESSAGE + message);
            }else{
                //incorrect data
                router.setRedirectType();
                router.setCurrentPage(GO_TO_SHOW_CART + REDIRECT_MESSAGE + ADDING_TO_CART_FAIL_INCORRECT_DATA);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "AddToCardCommand service exception. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, "AddToCardCommand service exception" + e);
            router.setCurrentPage(ERROR500);
        }


        return router;
    }

}
