package by.makei.shop.model.command.impl.admin;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
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
import static by.makei.shop.model.command.PagePath.GO_TO_UPDATE_PRODUCT;
import static by.makei.shop.model.command.RedirectMessage.*;

public class UpdatePhotoCommand implements Command {
    private static final String ERROR = "UpdatePhotoCommand Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        ProductService productService = ProductServiceImpl.getInstance();
        Router router = new Router();
        Map<String, String> productDataMap = new HashMap();

        productDataMap.put(ID, request.getParameter(ID));

        byte[] bytesPhoto;
        try (
                InputStream stream = request.getPart(PHOTO).getInputStream()) {
            bytesPhoto = stream.readAllBytes();
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, "error while updatePhotoCommand is trying to get photo. {}", e.getMessage());
            throw new CommandException("updatePhotoCommand error. {}", e);
        }
        try {
            if (parameterValidator.validatePhoto(productDataMap, bytesPhoto)) {
                productService.updatePhoto(productDataMap.get(ID), bytesPhoto);
                router.setRedirectType();
                router.setCurrentPage(GO_TO_UPDATE_PRODUCT+REDIRECT_MESSAGE+UPDATE_SUCCESS+REDIRECT_ID+request.getParameter(ID));
            }else {
                logger.log(Level.INFO, "UpdatePhotoCommand incorrect photo");
                router.setRedirectType();
                router.setCurrentPage(GO_TO_UPDATE_PRODUCT+REDIRECT_MESSAGE+UPDATE_FAIL+REDIRECT_ID+request.getParameter(ID));
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "GoToUpdateProductCommand. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
        }
        return router;

    }
}
