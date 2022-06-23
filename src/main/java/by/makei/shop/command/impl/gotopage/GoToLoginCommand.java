package by.makei.shop.command.impl.gotopage;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import static by.makei.shop.command.PagePath.LOGINATION;

public class GoToLoginCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        logger.log(Level.DEBUG, "GoToLogination currentPage :{}", currentPage);
        request.getSession().setAttribute(AttributeName.CURRENT_PAGE, currentPage);
        Router router = new Router();
        router.setCurrentPage(LOGINATION);
        router.setRedirectType();
        return router;
    }
}
