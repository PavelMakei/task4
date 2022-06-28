package by.makei.shop.command.impl.admin;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.AccessLevel;
import by.makei.shop.model.entity.Order;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import by.makei.shop.util.MessageReinstall;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.makei.shop.command.PagePath.ERROR500;
import static by.makei.shop.command.PagePath.ORDERS;

public class ShowOrderCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        Map<Order, String[]> orderMap = new LinkedHashMap<>();
        String currentPage = PagePathExtractor.extractAndSetToSessionPagePathAndContextPath(request);
        logger.log(Level.DEBUG, "ShowOrderCommand currentPage :{}", currentPage);
        MessageReinstall.extractAndSetMessage(AttributeName.MESSAGE, request);
        Map<String, String[]> inputParamRaw = request.getParameterMap();
        Map<String, String> incomeParam;
        incomeParam = inputParamRaw.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue()[0]));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AttributeName.USER);
        if (user.getAccessLevel().equals(AccessLevel.USER)) {
            incomeParam.put(AttributeName.USER_ID, String.valueOf(user.getId()));//doesn't need to be validated
        }//else (for ADMIN) will be used all ids, and returned all user's orders
        try {
            if (!userService.findOrderMapByParam(orderMap, incomeParam)) {
                logger.log(Level.ERROR, "ShowOrderCommand incorrect income params");
                router.setCurrentPage(ERROR500);
            }
            router.setCurrentPage(ORDERS);
            request.setAttribute(AttributeName.ORDER_MAP, orderMap);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ShowOrderCommand command error {}", e.getMessage());
            throw new CommandException("ShowOrderCommand command error", e);
        }
        return router;
    }
}
