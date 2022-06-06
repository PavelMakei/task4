package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.util.MessageReinstall;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import static by.makei.shop.model.command.AttributeName.CURRENT_PAGE;
import static by.makei.shop.model.command.AttributeName.MESSAGE;
import static by.makei.shop.model.command.PagePath.ADD_NEW_PRODUCT;


public class GoToAddNewProductCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        logger.log(Level.DEBUG, "GoToAddNewProductCommand currentPage :{}", currentPage);
        session.setAttribute(CURRENT_PAGE, currentPage);
        MessageReinstall.extractAndSetMessage(MESSAGE, request);
        router.setCurrentPage(ADD_NEW_PRODUCT);
        return router;
    }
}
