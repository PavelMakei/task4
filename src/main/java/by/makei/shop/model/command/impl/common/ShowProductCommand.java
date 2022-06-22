package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.Brand;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.ProductType;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;
import static by.makei.shop.model.command.PagePath.SHOW_PRODUCT;

public class ShowProductCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        Product product;
        ProductType productType;
        Brand brand;
        String id = request.getParameter(ID);
        ProductService productService = ProductServiceImpl.getInstance();

        try {
            product = productService.findProductById(id);
            brand = productService.findBrandById(String.valueOf(product.getBrandId()));
            productType = productService.findProductTypeById(String.valueOf(product.getTypeId()));

            request.setAttribute(PRODUCT, product);
            request.setAttribute(BRAND_NAME, brand.getBrandName());
            request.setAttribute(TYPE_NAME, productType.getTypeName());
            router.setCurrentPage(SHOW_PRODUCT);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ShowProductCommand command error. {}", e.getMessage());
            throw new CommandException("ShowProductCommand command error",e);
        }
        return router;
    }
}
