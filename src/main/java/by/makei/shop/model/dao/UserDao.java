package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.Cart;
import by.makei.shop.model.entity.Order;
import by.makei.shop.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao extends BaseDao<User>{

    /**
     * find user
     * @param login String {@link User} login
     * @param hashPassword String hashPassword
     * @return Optional
     * @throws DaoException
     */
    Optional<User> findUserByLoginAndPassword(String login, String hashPassword) throws DaoException;

    /**
     * create {@link User}
     * @param user filled {@link User}
     * @param hashPassword String hashPassword
     * @return boolean as result
     * @throws DaoException
     */
    boolean create(User user, String hashPassword) throws DaoException;

    /**
     * update {@link User} accessLevel
     * @param userDataMap should contain pairs user id param and value, access level param and new access level value
     * @return boolean as result
     * @throws DaoException
     */
    boolean updateAccessLevel(Map<String, String> userDataMap) throws DaoException;

    /**
     * update user's hashPassword
     * find user by email
     * @param email as String
     * @param hashPassword - new hashPassword as String
     * @return boolean as result
     * @throws DaoException
     */
    boolean updatePassword(String email, String hashPassword) throws DaoException;

    /**
     * find user by id, update all others fields
     * @param userDataMap set of pairs field name - value
     * @return boolean as result
     * @throws DaoException
     */
    boolean updateProfile(Map<String, String> userDataMap) throws DaoException;

    /**
     * find user by id, set money amount = resultAmount
     * @param currentUserId id as int
     * @param resultAmount amount to set
     * @return boolean as result
     * @throws DaoException
     */
    boolean updateMoneyAmount(int currentUserId, BigDecimal resultAmount) throws DaoException;

    /**
     * create order, create order-products tables.
     * @param currentUser {@link User}
     * @param currentCart {@link Cart}
     * @param orderDataMap  set of pairs field name - value to create order
     * @return boolean as value
     * @throws DaoException
     */
    boolean createOrderTransaction(User currentUser, Cart currentCart, Map<String, String> orderDataMap) throws DaoException;

    /**
     * find all user and group with total order (with status "DELIVERED") products and total price sum
     * @return map {@link User}, decimal[0] - bought products quantity, double[1] - total price sum
     * @throws DaoException
     */
    Map<User, double[]> findAllUsersOrdersSum() throws DaoException;

    boolean findOrderByParam(List<Order> orderList, Map<String, String> incomeParam) throws DaoException;

    boolean findOrderProductMap(int orderId, Map<Integer, Integer> productIdQuantity) throws DaoException;

    boolean cancelOrderTransaction(Order order) throws DaoException;

    boolean deliveryOrder(Order order) throws DaoException;

    boolean findOrderMapByParam(Map<Order, String[]> orderMap, Map<String, String> incomeParam) throws DaoException;
}

