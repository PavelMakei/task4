package by.makei.shop.model.command.impl.admin;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.entity.Order;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.util.MessageReinstall;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ERROR500;
import static by.makei.shop.model.command.PagePath.ORDERS;

public class ShowOrderCommand implements Command {
    private static final String ERROR = "ShowOrderCommand Service exception : ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        List<Order> orderList = new ArrayList<>();
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        logger.log(Level.DEBUG, "ShowOrderCommand currentPage :{}", currentPage);
        MessageReinstall.extractAndSetMessage(MESSAGE, request);
        Map<String, String[]> inputParamRaw = request.getParameterMap();
        Map<String, String> incomeParam;
        incomeParam = inputParamRaw.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue()[0]));
        try {
            if (!userService.findOrderByParam(orderList, incomeParam)) {
                logger.log(Level.ERROR, "ShowOrderCommand incorrect income params");
                router.setCurrentPage(ERROR500);
            }
            router.setCurrentPage(ORDERS);
            request.setAttribute(ORDER_LIST, orderList);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ShowOrderCommand. {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, ERROR + e.getMessage());
            router.setCurrentPage(ERROR500);
        }
        return router;
    }
}
