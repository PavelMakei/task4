package by.makei.shop.model.command.impl.admin;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.PagePath;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.AdminService;
import by.makei.shop.model.service.impl.AdminServiceImpl;
import by.makei.shop.model.validator.ParameterValidator;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;

public class AddNewProduct implements Command {
    private static final String ERROR = "AddNewProductCommand Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        AdminService adminService = new AdminServiceImpl();
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
        productDataMap.put(QUANTITY, request.getParameter(QUANTITY));

        byte[] bytesPhoto;
        try (
                InputStream stream = request.getPart(PHOTO).getInputStream()) {
            bytesPhoto = stream.readAllBytes();
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, "error while addNewProductCommand is trying to get photo. {}", e.getMessage());
            throw new CommandException("addNewProductCommand error. {}", e);
        }

        try {
            if(parameterValidator.validateProductData(productDataMap,bytesPhoto)) {
                adminService.addNewProduct(productDataMap, bytesPhoto);

                //TODO переход по редирект и/или на страницу с уведомлением? На index?
                router.setCurrentPage(PagePath.TEMP);
                //TODO only for test - place right page!!!
            }
            else{
                //если невалидно, снова получаем данные по брендам и типам, записываем старые значения и проблемы в реквест, возвращаемся на страницу добавления продукта
                Map<String,Integer> brands;
                Map<String,Integer> types;
                brands = adminService.getAllBrandsMap();
                types = adminService.getAllTypesMap();
                request.setAttribute(BRANDS_MAP, brands);
                request.setAttribute(TYPES_MAP, types);


                for(Map.Entry<String,String> entry: productDataMap.entrySet()){
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                router.setCurrentPage(PagePath.ADDNEWPRODUCT);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR,"GoToAddNewProductCommand command error. {}",e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
        }
        return router;
    }
}
