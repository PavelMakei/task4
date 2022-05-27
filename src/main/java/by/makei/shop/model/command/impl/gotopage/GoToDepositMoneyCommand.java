package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import static by.makei.shop.model.command.AttributeName.CURRENT_PAGE;
import static by.makei.shop.model.command.PagePath.DEPOSIT_MONEY;

public class GoToDepositMoneyCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        String currentPage = PagePathExtractor.extractPagePath(request);
        logger.log(Level.DEBUG,"GoToDepositMoney currentPage :{}",currentPage);
        request.getSession().setAttribute(CURRENT_PAGE,currentPage);
        Router router = new Router();
        router.setCurrentPage(DEPOSIT_MONEY);
        router.setRedirectType();
        return router;
    }
}
