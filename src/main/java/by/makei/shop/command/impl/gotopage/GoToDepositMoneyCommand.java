package by.makei.shop.command.impl.gotopage;

import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import static by.makei.shop.command.PagePath.DEPOSIT_MONEY;

public class GoToDepositMoneyCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        logger.log(Level.DEBUG, "GoToDepositMoney currentPage :{}", currentPage);
        Router router = new Router();
        router.setCurrentPage(DEPOSIT_MONEY);
        router.setRedirectType();
        return router;
    }
}
