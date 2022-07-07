package by.makei.shop.model.service;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.Cart;
import by.makei.shop.model.entity.Order;
import by.makei.shop.model.entity.User;
import by.makei.shop.util.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    /**
     * find user by login and password
     *
     * @param login    as String
     * @param password as String
     * @return optional
     * @throws ServiceException
     */
    Optional<User> signIn(String login, String password) throws ServiceException;

    /**
     * check input params and if correct create user, else add incorrect markers into userData map
     *
     * @param userData Map with income data
     * @param session
     * @return if input data incorrect - false
     * @throws ServiceException
     */
    boolean createUser(Map<String, String> userData, HttpSession session) throws ServiceException;

    /**
     * find all users
     *
     * @return List of user
     * @throws ServiceException
     */
    List<User> findAllUser() throws ServiceException;

    /**
     * find all users, quantity of products and total price sum in {@link Order} with status 'DELIVERED'
     *
     * @return Map <{@link User}, double[0] - quantity of bought products, double[1] - total price sum
     * @throws ServiceException
     */
    Map<User, double[]> findAllUserOrderSum() throws ServiceException;

    /**
     * check if income params are correct and if correct - try to update AccessLevel and return result as boolean
     * else mark incorrect data and return false
     *
     * @param userDataMap
     * @return
     * @throws ServiceException
     */
    boolean updateAccessLevel(Map<String, String> userDataMap) throws ServiceException;

    /**
     * find user by email
     *
     * @param email as String
     * @return Optional {@link User}
     * @throws ServiceException
     */
    Optional<User> findUserByEmail(String email) throws ServiceException;

    /**
     * encoding password to hashedPassword {@link PasswordEncoder} and try to update it by email {@link User}
     *
     * @param userDataMap
     * @return boolean result
     * @throws ServiceException
     */
    boolean updatePassword(Map<String, String> userDataMap) throws ServiceException;

    /**
     * try to update {@link User} profile
     *
     * @param userDataMap
     * @return boolean result
     * @throws ServiceException
     */
    boolean updateProfile(Map<String, String> userDataMap) throws ServiceException;

    /**
     * find {@link User} by one parameter (ID, EMAIL, LOGIN...) if number of user have the same pair param - value, returns first found
     *
     * @param paramName
     * @param paramValue
     * @return Optional {@link User}
     * @throws ServiceException
     */
    Optional<User> findUserByOneParam(String paramName, String paramValue) throws ServiceException;

    /**
     * update user's money amount with sum of currentUserAmount and amountToDeposit
     *
     * @param currentUserId     as int
     * @param currentUserAmount as BigDecimal
     * @param amountToDeposit   as String
     * @return boolean result
     * @throws ServiceException
     */
    boolean updateUserMoneyAmount(int currentUserId, BigDecimal currentUserAmount, String amountToDeposit) throws ServiceException;

    /**
     * check if input data are not correct add MESSAGE ORDERING_FAIL_INCORRECT_DATA to orderDataMap, return false
     * check if user has enough money to make this transaction, if not - add MESSAGE ORDERING_FAIL_NOT_ENOUGH_PRODUCTS to orderDataMap, return false
     * try to make transaction and return boolean result
     *
     * @param currentUser  as {@link User}
     * @param currentCart  as currentUser {@link Cart}
     * @param orderDataMap set of pairs field name - value to create order
     * @return boolean as result
     * @throws ServiceException
     */
    boolean createOrder(User currentUser, Cart currentCart, Map<String, String> orderDataMap) throws ServiceException;

    /**
     * check if quantity of ordered (in {@link Cart}) products <= quantity these products in stock
     * if not - correct quantity in currentCart = quantity in stock. If quantity in stock == 0< remove this product from currentCart and return false
     *
     * @param currentCart
     * @return boolean as result
     * @throws ServiceException
     */
    boolean isStillEnoughProductInStock(Cart currentCart) throws ServiceException;

    boolean validateAndMarkIncomeData(Map<String, String> incomeDataMap) throws ServiceException;

    /**
     * check if input parameters are valid. If not - mark incorrect and return false.
     * find orders by param and fill them into orderList
     *
     * @param orderList   {@link Order} as List
     * @param incomeParam (orders.ID, USER_ID,STATUS) if there is no parameter - will be used % to find all
     * @return boolean as result
     * @throws ServiceException
     */
    boolean findOrderByParam(List<Order> orderList, Map<String, String> incomeParam) throws ServiceException;

    /**
     * @param orderMap    empty Orders as key and String[] as value
     *                    String[0] - User.login, String[1] - total sum of order
     * @param incomeParam - Product.id, User.id, Product.status. If is empty will be found all
     * @return filled income orderMap
     * @throws ServiceException
     */
    boolean findOrderMapByParam(Map<Order, String[]> orderMap, Map<String, String> incomeParam) throws ServiceException;

    /**
     * check if input params are correct. If not - mark incorrect, return false.
     * try to make cancel transaction, return result
     *
     * @param request should consist ID
     * @return boolean as result
     * @throws ServiceException
     */
    boolean cancelOrder(HttpServletRequest request) throws ServiceException;

    /**
     * check if input parameter is correct. If not - return false.
     * try to change order status, return result
     *
     * @param request should consist ID ({@link Order})
     * @return boolean as result
     * @throws ServiceException
     */
    boolean deliveryOrder(HttpServletRequest request) throws ServiceException;

    /**
     * check if input parameter is correct. If not - return false.
     * try to delete user by id, return result
     * @param request should consist ID ({@link User})
     * @return boolean as result
     * @throws ServiceException
     */
    boolean delete(HttpServletRequest request) throws ServiceException;

}
