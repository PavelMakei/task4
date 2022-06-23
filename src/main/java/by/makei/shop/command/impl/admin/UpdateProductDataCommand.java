package by.makei.shop.command.impl.admin;

import by.makei.shop.command.*;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.command.PagePath.GO_TO_UPDATE_PRODUCT;

public class UpdateProductDataCommand implements Command {

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
        productDataMap.put(AttributeName.ID, request.getParameter(AttributeName.ID));
        productDataMap.put(AttributeName.PHOTO_STRING, request.getParameter(AttributeName.PHOTO_STRING));

        try {
            if (productService.updateProductData(productDataMap)) {
                router.setRedirectType();
                router.setCurrentPage(GO_TO_UPDATE_PRODUCT + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.UPDATE_SUCCESS + RedirectMessage.REDIRECT_ID + request.getParameter(AttributeName.ID));
            } else {
                logger.log(Level.INFO, "UpdateProductDataCommand incorrect data");
                Map<String, String> brands;
                Map<String, String> types;
                brands = productService.findAllBrandsMap();
                types = productService.findAllTypesMap();
                request.setAttribute(AttributeName.BRANDS_MAP, brands);
                request.setAttribute(AttributeName.TYPES_MAP, types);
                request.setAttribute(AttributeName.MESSAGE, RedirectMessage.UPDATE_FAIL);
                for (Map.Entry<String, String> entry : productDataMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                router.setCurrentPage(PagePath.UPDATE_PRODUCT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "UpdateProductDataCommand command error {}", e.getMessage());
            throw new CommandException("UpdateProductDataCommand command error", e);
        }
        return router;

    }
}
