package by.makei.shop.command.impl.gotopage;

import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import static by.makei.shop.command.PagePath.CHECKOUT;

public class GoToCheckoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        logger.log(Level.DEBUG, "GoToCheckoutCommand currentPage :{}", currentPage);
        Router router = new Router();
        router.setCurrentPage(CHECKOUT);
        router.setRedirectType();
        return router;
    }

}
