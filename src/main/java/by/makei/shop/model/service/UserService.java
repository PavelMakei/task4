package by.makei.shop.model.service;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.Cart;
import by.makei.shop.model.entity.Order;
import by.makei.shop.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    //TODO
    Optional<User> signIn(String login, String password) throws ServiceException;
    boolean createUser(Map<String, String> userData, HttpSession session) throws ServiceException;

    List<User> findAllUser() throws ServiceException;

    boolean updateAccessLevel(Map<String, String> userDataMap) throws ServiceException;

    Optional<User>findUserByEmail(String email) throws ServiceException;

    boolean updatePassword(Map<String, String> userDataMap) throws ServiceException;

    boolean updateProfile(Map<String, String> userDataMap) throws ServiceException;

    Optional<User> findUserByOneParam(String paramName, String paramValue) throws ServiceException;


    boolean updateUserMoneyAmount(int currentUserId, BigDecimal currentUserAmount, String amountToDeposit) throws ServiceException;

    boolean createOrder(User currentUser, Cart currentCart, Map<String, String> orderDataMap) throws ServiceException;

    boolean validateAndMarkIncomeData(Map<String, String> incomeDataMap) throws ServiceException;

    boolean findOrderByParam(List<Order> orderList, Map<String, String> incomeParam) throws ServiceException;

    boolean cancelOrder(HttpServletRequest request) throws ServiceException;
}
