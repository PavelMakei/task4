package by.makei.shop.model.command.impl.admin;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.PagePath;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;
import static by.makei.shop.model.command.PagePath.GO_TO_UPDATE_PRODUCT;
import static by.makei.shop.model.command.RedirectMessage.*;

public class UpdateProductDataCommand implements Command {
    private static final String ERROR = "UpdateProductDataCommand Service exception : ";
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
        productDataMap.put(ID, request.getParameter(ID));
        productDataMap.put(PHOTO_STRING, request.getParameter(PHOTO_STRING));

        try {
            if (productService.updateProductData(productDataMap)){
                router.setRedirectType();
                router.setCurrentPage(GO_TO_UPDATE_PRODUCT+REDIRECT_MESSAGE+UPDATE_SUCCESS+REDIRECT_ID+request.getParameter(ID));
            }else {
                logger.log(Level.INFO, "UpdateProductDataCommand incorrect data");
                Map<String,String> brands;
                Map<String,String> types;
                brands = productService.findAllBrandsMap();
                types = productService.findAllTypesMap();
                request.setAttribute(BRANDS_MAP, brands);
                request.setAttribute(TYPES_MAP, types);
                request.setAttribute(MESSAGE,UPDATE_FAIL);
                for(Map.Entry<String,String> entry: productDataMap.entrySet()){
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                router.setCurrentPage(PagePath.UPDATE_PRODUCT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "UpdateProductDataCommand. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
        }
        return router;

    }
}
