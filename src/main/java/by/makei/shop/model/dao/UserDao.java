package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.Cart;
import by.makei.shop.model.entity.Order;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {

    /**
     * find user
     *
     * @param login        String {@link User} login
     * @param hashPassword String hashPassword
     * @return Optional
     * @throws DaoException
     */
    Optional<User> findUserByLoginAndPassword(String login, String hashPassword) throws DaoException;

    /**
     * create {@link User}
     *
     * @param user         filled {@link User}
     * @param hashPassword String hashPassword
     * @return boolean as result
     * @throws DaoException
     */
    boolean create(User user, String hashPassword) throws DaoException;

    /**
     * update {@link User} accessLevel
     *
     * @param userDataMap should contain pairs user id param and value, access level param and new access level value
     * @return boolean as result
     * @throws DaoException
     */
    boolean updateAccessLevel(Map<String, String> userDataMap) throws DaoException;

    /**
     * update user's hashPassword
     * find user by email
     *
     * @param email        as String
     * @param hashPassword - new hashPassword as String
     * @return boolean as result
     * @throws DaoException
     */
    boolean updatePassword(String email, String hashPassword) throws DaoException;

    /**
     * find user by id, update all others fields
     *
     * @param userDataMap set of pairs field name - value
     * @return boolean as result
     * @throws DaoException
     */
    boolean updateProfile(Map<String, String> userDataMap) throws DaoException;

    /**
     * find user by id, set money amount = resultAmount
     *
     * @param currentUserId id as int
     * @param resultAmount  amount to set
     * @return boolean as result
     * @throws DaoException
     */
    boolean updateMoneyAmount(int currentUserId, BigDecimal resultAmount) throws DaoException;

    /**
     * create order, create order-products tables.
     *
     * @param currentUser  {@link User}
     * @param currentCart  {@link Cart}
     * @param orderDataMap set of pairs field name - value to create order
     * @return boolean as value
     * @throws DaoException
     */
    boolean createOrderTransaction(User currentUser, Cart currentCart, Map<String, String> orderDataMap) throws DaoException;

    /**
     * find all user and group with total order (with status "DELIVERED") products and total price sum
     *
     * @return map {@link User}, decimal[0] - bought products quantity, double[1] - total price sum
     * @throws DaoException
     */
    Map<User, double[]> findAllUsersOrdersSum() throws DaoException;

    /**
     * find {@link Order} by parameters (order id, productId, orderStatus) if the parameter is empty, it wouldn't be used as filter
     *
     * @param orderList   list to fill with {@link Order}
     * @param incomeParam set of pairs parameter - value ({@link Order}id, userId, orderStatus)
     * @return boolean as result
     * @throws DaoException
     */
    boolean findOrderByParam(List<Order> orderList, Map<String, String> incomeParam) throws DaoException;

    /**
     * find products in order by its id, fill Map productIdQuantity set of pairs
     *
     * @param orderId           {@link Order} id
     * @param productIdQuantity map productId - quantity in order
     * @return boolean as result
     * @throws DaoException
     */
    boolean findOrderProductMap(int orderId, Map<Integer, Integer> productIdQuantity) throws DaoException;

    /**
     * return paid money to {@link User}, return {@link Product} to stock
     *
     * @param order {@link Order}
     * @return boolean as result
     * @throws DaoException
     */
    boolean cancelOrderTransaction(Order order) throws DaoException;

    /**
     * Change {@link Order} status to "DELIVERED"
     *
     * @param order {@link Order}
     * @return boolean as result
     * @throws DaoException
     */
    boolean deliveryOrder(Order order) throws DaoException;

    /**
     * find orders  by parameters (order id, productId, orderStatus) if the parameter is empty, it wouldn't be used as filter
     *
     * @param orderMap    Map {@link Order}, String {@link User} login, String total order price sum
     * @param incomeParam set of pairs parameter - value ({@link Order}id, userId, orderStatus)
     * @return boolean as result
     * @throws DaoException
     */
    boolean findOrderMapByParam(Map<Order, String[]> orderMap, Map<String, String> incomeParam) throws DaoException;
}

