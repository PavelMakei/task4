package by.makei.shop.command.impl.admin;

import by.makei.shop.command.*;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddNewProductCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ProductService productService = ProductServiceImpl.getInstance();
        Router router = new Router();

        Map<String, String> productDataMap = new HashMap();
        productDataMap.put(AttributeName.BRAND_ID, request.getParameter(AttributeName.BRAND_ID));
        productDataMap.put(AttributeName.TYPE_ID, request.getParameter(AttributeName.TYPE_ID));
        productDataMap.put(AttributeName.PRODUCT_NAME, request.getParameter(AttributeName.PRODUCT_NAME));
        productDataMap.put(AttributeName.DESCRIPTION, request.getParameter(AttributeName.DESCRIPTION));
        productDataMap.put(AttributeName.PRICE, request.getParameter(AttributeName.PRICE));
        productDataMap.put(AttributeName.COLOUR, request.getParameter(AttributeName.COLOUR));
        productDataMap.put(AttributeName.POWER, request.getParameter(AttributeName.POWER));
        productDataMap.put(AttributeName.SIZE, request.getParameter(AttributeName.SIZE));
        productDataMap.put(AttributeName.QUANTITY_IN_STOCK, request.getParameter(AttributeName.QUANTITY_IN_STOCK));
        byte[] bytesPhoto;
        try (
                InputStream stream = request.getPart(AttributeName.PHOTO).getInputStream()) {
            bytesPhoto = stream.readAllBytes();
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, "error while addNewProductCommand is trying to get photo. {}", e.getMessage());
            throw new CommandException("addNewProductCommand error. {}", e);
        }
        try {
            if (productService.addNewProduct(productDataMap, bytesPhoto)) {
                router.setRedirectType();
                router.setCurrentPage(PagePath.GO_TO_ADD_NEW_PRODUCT + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.SUCCESSFULLY_ADDED);
            } else {
                logger.log(Level.ERROR, "Product wasn't validated, cancel adding");
                for (Map.Entry<String, String> entry : productDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.setAttribute(AttributeName.MESSAGE, RedirectMessage.ADDITION_FAILED);
                router.setCurrentPage(PagePath.ADD_NEW_PRODUCT);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "GoToAddNewProductCommand command error. {}", e.getMessage());
            throw new CommandException("GoToAddNewProductCommand command error", e);
        }
        return router;
    }
}
