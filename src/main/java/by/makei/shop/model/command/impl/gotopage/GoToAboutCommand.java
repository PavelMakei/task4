package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import static by.makei.shop.model.command.PagePath.ABOUT;

public class GoToAboutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router= new Router();
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        logger.log(Level.DEBUG, "GoToAbout currentPage :{}", currentPage);
        router.setCurrentPage(ABOUT);
        return router;
    }
}
