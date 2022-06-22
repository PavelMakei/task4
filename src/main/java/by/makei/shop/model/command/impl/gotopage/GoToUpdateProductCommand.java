package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import by.makei.shop.util.MessageReinstall;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;
import static by.makei.shop.model.command.PagePath.UPDATE_PRODUCT;

public class GoToUpdateProductCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> inputProductIdQuantity = new HashMap<>();
        Map<Product, String> productQuantity = new HashMap<>();
        inputProductIdQuantity.put(ID, request.getParameter(ID));

        logger.log(Level.DEBUG, "GoToUpdateProductCommand get product id :{}", request.getParameter(ID));
        ProductService productService = ProductServiceImpl.getInstance();
        Router router = new Router();
        try {
            if (!productService.findMapProductQuantityById(inputProductIdQuantity, productQuantity)) {
                logger.log(Level.ERROR, "GoToUpdateProductCommand. Incorrect id");
                router.setCurrentPage(ERROR500);
            } else {
                request.setAttribute(PRODUCT, productQuantity.keySet().toArray()[0]);
                request.setAttribute(QUANTITY, productQuantity.values().toArray()[0]);
                MessageReinstall.extractAndSetMessage(MESSAGE, request);
                router.setCurrentPage(UPDATE_PRODUCT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "GoToUpdateProductCommand command error {}", e.getMessage());
            throw new CommandException("GoToUpdateProductCommand command error", e);
        }

        return router;
    }
}
