package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import by.makei.shop.model.validator.ValidatorPattern;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.*;

public class GoToUpdateProductCommand implements Command {
    private static final String ERROR = "GoToUpdateProductCommand Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        String productId = request.getParameter(ID);
        logger.log(Level.DEBUG, "GoToUpdateProductCommand get product id :{}", productId);
        ProductService productService = new ProductServiceImpl();
        Map<Product, String> productQuantity = new HashMap<>();
        Map<String, String> brands;
        Map<String, String> types;
        Router router = new Router();
        try {
            productQuantity = productService.findMapProductQuantityById(productId);
            brands = productService.findAllBrandsMap();
            types = productService.findAllTypesMap();
            request.setAttribute(PRODUCT, productQuantity.keySet().toArray()[0]);
            request.setAttribute(QUANTITY, productQuantity.values().toArray()[0]);
            //TODO add MESSAGE
            request.setAttribute(BRANDS_MAP, brands);
            request.setAttribute(TYPES_MAP, types);
            router.setCurrentPage(UPDATEPRODUCT);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "GoToUpdateProductCommand. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
        }

        return router;
    }
}
