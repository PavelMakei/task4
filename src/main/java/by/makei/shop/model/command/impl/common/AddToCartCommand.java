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
import static by.makei.shop.model.command.PagePath.ERROR500;
import static by.makei.shop.model.command.PagePath.SHOW_CART;

public class AddToCartCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        ProductService productService;
        Cart cart;
        Map<String, String> inputProductIdQuantity = new HashMap<>();
        Map<Product, String> productQuantityMapFromDB;
        inputProductIdQuantity.put(ID, request.getParameter(ID));
        inputProductIdQuantity.put(QUANTITY, request.getParameter(QUANTITY));
        ParameterValidator validator = ParameterValidatorImpl.getInstance();
        cart = (Cart) session.getAttribute(SESS_CART);
        int currentQuantity = 0;
        int inputQuantity;
        int quantityInDb = 0;
        String message;
        Product product = null;

        try {
            if (validator.validateAndMarkProductData(inputProductIdQuantity)) {
                productService = ProductServiceImpl.getInstance();
                inputQuantity = Integer.parseInt(inputProductIdQuantity.get(QUANTITY));
                productQuantityMapFromDB = productService.findMapProductQuantityById(inputProductIdQuantity.get(ID));
                if (productQuantityMapFromDB.size() != 1) {
                    logger.log(Level.WARN, "AddToCartCommand error while find product in DB, quantity of product = {}", productQuantityMapFromDB.size());
                    request.setAttribute(ERROR_MESSAGE, "AddToCartCommand error while find product in DB, quantity of product = " + productQuantityMapFromDB.size());
                    router.setCurrentPage(ERROR500);
                    return router;
                }
                for (Map.Entry entry : productQuantityMapFromDB.entrySet()) {
                    product = (Product) entry.getKey();
                    quantityInDb = Integer.parseInt((String) entry.getValue());
                }
                if (cart.getProductQuantity().containsKey(product)) {
                    currentQuantity = cart.getProductQuantity().get(product);
                }
                currentQuantity += inputQuantity;
                if (currentQuantity > Cart.MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY) {
                    logger.log(Level.INFO, "AddToCartCommand asked product quantity more then " + Cart.MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY);
                    currentQuantity = Cart.MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY;
                    message = "You can not buy more then " + Cart.MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY; //TODO message
                } else if (currentQuantity > quantityInDb) {
                    logger.log(Level.INFO, "AddToCartCommand asked product quantity more then in stock");
                    currentQuantity = quantityInDb;
                    message = "Maximum of product in stock added to cart"; //TODO message
                } else {
                    message = "Product added to cart"; //TODO message
                }
                cart.setProductQuantity(product, currentQuantity);
                session.setAttribute(SESS_CART, cart);
                //TODO идём на страницу простмотра ордеров
//                request.setAttribute(MESSAGE, message);
                session.setAttribute(SESS_MESSAGE,message);
                //////////
                router.setRedirectType();
                /////////

                router.setCurrentPage(SHOW_CART);
                //При покупке открывать отдельное мелкое окно и в него выводить результат операции
                //выполнение команды в родительском окне по переходу в корзину?
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "AddToCardCommand service exception. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, "AddToCardCommand service exception" + e);
            router.setCurrentPage(ERROR500);
        }


        return router;
    }

}
