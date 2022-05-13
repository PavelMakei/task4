package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.*;
import static by.makei.shop.model.validator.DefaultSearchParam.*;

public class GoToMain implements Command {

    private static final String ERROR = "GotoMain Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> brands;
        Map<String, String> types;
        Map<Product, String> productQuantityMap;
        Map<String, String> searchAttr = new HashMap();
        Router router = new Router();
        ProductServiceImpl productService = new ProductServiceImpl();
        HttpSession session = request.getSession();
        String currentPage = PagePathExtractor.extractPagePath(request);
        session.setAttribute(CURRENT_PAGE, currentPage);

        searchAttr.put(SEARCH_BRAND_ID, DEFAULT_BRAND_ID);
        searchAttr.put(SEARCH_TYPE_ID, DEFAULT_TYPE_ID);
        searchAttr.put(SEARCH_MIN_PRICE, DEFAULT_MIN_PRICE);
        searchAttr.put(SEARCH_MAX_PRICE, DEFAULT_MAX_PRICE);
        searchAttr.put(SEARCH_MIN_POWER, DEFAULT_MIN_POWER);
        searchAttr.put(SEARCH_MAX_POWER, DEFAULT_MAX_POWER);
        searchAttr.put(SEARCH_PAGE, DEFAULT_PAGE);

        Map<String, String[]> parametersMap = request.getParameterMap();
        for(Map.Entry<String,String[]> entry : parametersMap.entrySet()){
            searchAttr.put(entry.getKey(),entry.getValue()[0]);
        }
//TODO здесь в валидатор/корректор



//TODO здесь новый запрос в ДАО

//TODO здесь новый запрос в ДАО









        //TODO добавить в сессию аттрибуты запроса? В find? Добавить страницы и направление перхода?


        try {
//загрузить продукты
            productQuantityMap = productService.getAllProductMap();
            request.setAttribute(PRODUCTS_QUANTITY_MAP, productQuantityMap);
            brands = productService.getAllBrandsMap();
            types = productService.getAllTypesMap();
            request.setAttribute(BRANDS_MAP, brands);
            request.setAttribute(BRANDS_MAP, brands);
            request.setAttribute(TYPES_MAP, types);

            for(Map.Entry<String,String> entry: searchAttr.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }

            router.setCurrentPage(MAIN);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "GoToMain command error. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
        }
        return router;
    }
}
