package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import by.makei.shop.util.MessageReinstall;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ADD_NEW_PRODUCT;
import static by.makei.shop.model.command.PagePath.ERROR500;


public class GoToAddNewProductCommand implements Command {
    private static final String ERROR = "GoToAddNewProductCommand Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String,String> brands;
        Map<String,String> types;
        Router router = new Router();
        ProductService productService = ProductServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        session.setAttribute(CURRENT_PAGE,currentPage);
        MessageReinstall.extractAndSetMessage(MESSAGE,request);

        try {
            brands = productService.findAllBrandsMap();
            types = productService.findAllTypesMap();
            request.setAttribute(BRANDS_MAP, brands);
            request.setAttribute(TYPES_MAP, types);
            router.setCurrentPage(ADD_NEW_PRODUCT);
        } catch (ServiceException e) {
            logger.log(Level.ERROR,"GoToAddNewProductCommand command error. {}",e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
        }
        return router;
    }
}
