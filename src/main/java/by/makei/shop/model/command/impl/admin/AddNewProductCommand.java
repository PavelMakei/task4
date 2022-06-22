package by.makei.shop.model.command.impl.admin;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.PagePath;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;
import static by.makei.shop.model.command.RedirectMessage.*;

public class AddNewProductCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ProductService productService = ProductServiceImpl.getInstance();
        Router router = new Router();

        Map<String, String> productDataMap = new HashMap();
        productDataMap.put(BRAND_ID, request.getParameter(BRAND_ID));
        productDataMap.put(TYPE_ID, request.getParameter(TYPE_ID));
        productDataMap.put(PRODUCT_NAME, request.getParameter(PRODUCT_NAME));
        productDataMap.put(DESCRIPTION, request.getParameter(DESCRIPTION));
        productDataMap.put(PRICE, request.getParameter(PRICE));
        productDataMap.put(COLOUR, request.getParameter(COLOUR));
        productDataMap.put(POWER, request.getParameter(POWER));
        productDataMap.put(SIZE, request.getParameter(SIZE));
        productDataMap.put(QUANTITY_IN_STOCK, request.getParameter(QUANTITY_IN_STOCK));
        byte[] bytesPhoto;
        try (
                InputStream stream = request.getPart(PHOTO).getInputStream()) {
            bytesPhoto = stream.readAllBytes();
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, "error while addNewProductCommand is trying to get photo. {}", e.getMessage());
            throw new CommandException("addNewProductCommand error. {}", e);
        }
        try {
            if (productService.addNewProduct(productDataMap, bytesPhoto)) {
                router.setRedirectType();
                router.setCurrentPage(PagePath.GO_TO_ADD_NEW_PRODUCT + REDIRECT_MESSAGE + SUCCESSFULLY_ADDED);
            } else {
                logger.log(Level.INFO, "Product wasn't validated, cancel adding");
                for (Map.Entry<String, String> entry : productDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.setAttribute(MESSAGE, ADDITION_FAILED);
                router.setCurrentPage(PagePath.ADD_NEW_PRODUCT);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "GoToAddNewProductCommand command error. {}", e.getMessage());
            throw new CommandException("GoToAddNewProductCommand command error",e);
        }
        return router;
    }
}
