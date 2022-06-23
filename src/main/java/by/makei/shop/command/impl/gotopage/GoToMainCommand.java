package by.makei.shop.command.impl.gotopage;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static by.makei.shop.command.PagePath.MAIN;
import static by.makei.shop.model.validator.DefaultSearchParam.*;

public class GoToMainCommand implements Command {
    private static final String DESC = "DESC";
    private static final Map<String, String> orderByParamQuery = new LinkedHashMap();

    static {
        orderByParamQuery.put(AttributeName.ORDER_BY_PRICE_UP, AttributeName.PRICE);
        orderByParamQuery.put(AttributeName.ORDER_BY_PRICE_DN, AttributeName.PRICE + " " + DESC);
        orderByParamQuery.put(AttributeName.ORDER_BY_POWER_UP, AttributeName.POWER);
        orderByParamQuery.put(AttributeName.ORDER_BY_POWER_DN, AttributeName.POWER + " " + DESC);
        orderByParamQuery.put(AttributeName.ORDER_BY_NAME_UP, AttributeName.PRODUCT_NAME);
        orderByParamQuery.put(AttributeName.ORDER_BY_NAME_DN, AttributeName.PRODUCT_NAME + " " + DESC);
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<Product, String> productQuantityMap;
        Router router = new Router();
        ProductService productService = ProductServiceImpl.getInstance();
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        logger.log(Level.DEBUG, "GotoMainCommand currentPage :{}", currentPage);

        Map<String, String> searchAttr = new HashMap();
        searchAttr.put(AttributeName.SEARCH_BRAND_ID, DEFAULT_BRAND_ID);
        searchAttr.put(AttributeName.SEARCH_TYPE_ID, DEFAULT_TYPE_ID);
        searchAttr.put(AttributeName.SEARCH_MIN_PRICE, DEFAULT_MIN_PRICE);
        searchAttr.put(AttributeName.SEARCH_MAX_PRICE, DEFAULT_MAX_PRICE);
        searchAttr.put(AttributeName.SEARCH_MIN_POWER, DEFAULT_MIN_POWER);
        searchAttr.put(AttributeName.SEARCH_MAX_POWER, DEFAULT_MAX_POWER);
        searchAttr.put(AttributeName.SEARCH_PAGE, DEFAULT_PAGE);
        searchAttr.put(AttributeName.PAGE_BUTTON, DEFAULT_PAGE_BUTTON);
        searchAttr.put(AttributeName.ORDER_BY, DEFAULT_ORDER_BY);
        searchAttr.put(AttributeName.SEARCH_IN_STOCK, DEFAULT_IN_STOCK);

        Map<String, String[]> parametersMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parametersMap.entrySet()) {
            searchAttr.put(entry.getKey(), entry.getValue()[0]);
        }
        try {
//find products
            productQuantityMap = productService.findProductsByParam(searchAttr, orderByParamQuery);
            request.setAttribute(AttributeName.PRODUCTS_QUANTITY_MAP, productQuantityMap);
            request.setAttribute(AttributeName.ORDER_ARRAY, new LinkedList<String>(orderByParamQuery.keySet()));
//send (return) search params to page
            for (Map.Entry<String, String> entry : searchAttr.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            router.setCurrentPage(MAIN);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "GoToMain command error. {}", e.getMessage());
            throw new CommandException("GoToMain command error", e);
        }
        return router;
    }
}
