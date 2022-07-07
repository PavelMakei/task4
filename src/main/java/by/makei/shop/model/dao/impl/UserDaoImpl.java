package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.connectionpool.ProxyConnection;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.mapper.impl.OrderMapper;
import by.makei.shop.model.dao.mapper.impl.UserMapper;
import by.makei.shop.model.entity.Cart;
import by.makei.shop.model.entity.Order;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.User;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static by.makei.shop.command.AttributeName.*;

public class UserDaoImpl implements UserDao {
    private static final UserDaoImpl instance = new UserDaoImpl();
    private static final String FIND_ANY = "%";
    public static final String TOTAL_SUM = "total";
    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD = """
            SELECT id, first_name, last_name, login, password, email, phone, access_level, registration_date,money_amount
            FROM lightingshop.users WHERE
            login = ?
            AND
            password = ?""";
    private static final String SQL_ADD_NEW_USER = """
            INSERT INTO lightingshop.users(first_name, last_name, login, password, email, phone)
            VALUES(?,?,?,?,?,?)""";
    private static final String SQL_SELECT_USER_BY_VAR_PARAM = """
            SELECT id, first_name, last_name, login, password, email, phone, access_level, registration_date,money_amount
            FROM lightingshop.users WHERE %s = ?
            """;
    private static final String SQL_FIND_ALL_USER_ORDER_BY_PARAM = """
            SELECT id, first_name, last_name, login, password, email, phone, access_level, registration_date,money_amount FROM lightingshop.users
            ORDER BY last_name
            """;
    private static final String SQL_UPDATE_ACCESS_LEVEL = """
            UPDATE lightingshop.users
            SET access_level =?
            WHERE id =?
            """;
    private static final String SQL_UPDATE_PASSWORD_BY_EMAIL = """
            UPDATE lightingshop.users
            SET password =?
            WHERE email =?
            """;
    private static final String SQL_UPDATE_PROFILE = """
            UPDATE lightingshop.users
            SET first_name =?, last_name =?, login =?, email =?, phone =?
            WHERE id =?
            """;
    public static final String SQL_UPDATE_MONEY_AMOUNT = """
            UPDATE lightingshop.users
            SET money_amount =?
            WHERE id =?
            """;
    public static final String SQL_CREATE_ORDER = """
            INSERT INTO lightingshop.orders (user_id, address, phone, detail)
            VALUES (?,?,?,?)
            """;
    public static final String SQL_FIND_LAST_ORDER_ID = """
            SELECT LAST_INSERT_ID() FROM lightingshop.orders
            """;
    public static final String SQL_CHANGE_PRODUCT_QUANTITY = """
            UPDATE lightingshop.products SET quantity_in_stock = quantity_in_stock -? WHERE id = ?
            """;
    public static final String SQL_CREATE_ORDER_PRODUCTS = """
            INSERT INTO lightingshop.order_products (order_id, product_id, quantity) VALUES (?,?,?)
            """;
    public static final String SQL_CHANGE_USER_MONEY = """
            UPDATE lightingshop.users SET money_amount = money_amount - ? WHERE id =?
            """;
    public static final String SQL_FIND_ORDER_BY_PARAM = """
            SELECT id, user_id, address, phone, detail, status, date_open, date_close FROM lightingshop.orders
            WHERE id LIKE ?
            AND user_id LIKE ?
            AND status LIKE ?
            ORDER BY status, date_open
            """;
    public static final String SQL_FIND_PRODUCT_QUANTITY_BY_ORDER_ID = """
            SELECT product_id, quantity FROM lightingshop.order_products
            WHERE order_id =?
            """;
    public static final String SQL_UPDATE_ORDER_STATUS = """
            UPDATE lightingshop.orders
            SET 
            date_close =?,status =?
            WHERE id =?
            """;

    public static final String SQL_FIND_PRODUCT_BY_ID = """
            SELECT id, brand_id, type_id, product_name, description, price, colour, power, size, photo, quantity_in_stock
            FROM lightingshop.products
            WHERE id = ?
            """;
    public static final String SQL_FIND_ALL_USER_ORDER_SUM = """
            SELECT users.id, users.first_name, users.last_name, users.login, users.password, users.email, users.phone,
            users.access_level, users.registration_date,users.money_amount,
            SUM(CASE WHEN orders.status ='DELIVERED' THEN order_products.quantity END) AS number_prods,
            SUM(CASE WHEN orders.status ='DELIVERED' THEN products.price*order_products.quantity END) AS total_ords_sum FROM lightingshop.users
            LEFT JOIN lightingshop.orders ON users.id = orders.user_id
            LEFT JOIN lightingshop.order_products ON orders.id = order_products.order_id
            LEFT JOIN lightingshop.products on order_products.product_id = products.id
            GROUP BY users.id
            ORDER BY users.id;
            """;
    public static final String SQL_FIND_ORDER_SUM_BY_PARAM = """
             SELECT orders.id, orders.user_id, orders.address, orders.phone, orders.detail, orders.status, orders.date_open,
              orders.date_close, users.login,SUM(products.price*order_products.quantity) AS total FROM lightingshop.orders
             LEFT JOIN lightingshop.users ON orders.user_id = users.id
             LEFT JOIN lightingshop.order_products ON order_products.order_id = orders.id
             LEFT JOIN lightingshop.products ON order_products.product_id = products.id
                        WHERE orders.id LIKE ?
                        AND user_id LIKE ?
                        AND orders.status LIKE ?
                        GROUP BY orders.id, orders.status, orders.date_open
                        ORDER BY orders.status, orders.date_open;
            """;

    public static final String SQL_DELETE_USER = """
            DELETE users FROM users
            WHERE users.id = ?;""";

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String hashPassword) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, login.toLowerCase());
            preparedStatement.setString(2, hashPassword);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalUser = new UserMapper().mapEntity(resultSet);
                logger.log(Level.INFO, "user was found by login {} and hash password {}", login, hashPassword);
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error in findUserByLoginAndPassword");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return optionalUser;
    }

    @Override
    public boolean create(User user, String hashPassword) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_ADD_NEW_USER);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, hashPassword);
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.execute();
            logger.log(Level.INFO, "new user was created. \n---- {}", user);
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "UserDao error while create. {}", e.getMessage());
            throw new DaoException("UserDao error while create", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement);
        }
        return true;
    }

    @Override
    public boolean updateAccessLevel(Map<String, String> userDataMap) throws DaoException {
        int result = 0;
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(SQL_UPDATE_ACCESS_LEVEL);
            preparedStatement.setString(1, userDataMap.get(ACCESS_LEVEL));
            preparedStatement.setInt(2, Integer.parseInt(userDataMap.get(ID)));
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "UserDao error while updateAccessLevel. {}", e.getMessage());
            throw new DaoException("UserDao error while updateAccessLevel", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement);
        }
        return result == 1;
    }

    @Override
    public boolean updatePassword(String email, String hashPassword) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        proxyConnection = DbConnectionPool.getInstance().takeConnection();
        int result = 0;
        try {
            preparedStatement =
                    proxyConnection.prepareStatement(SQL_UPDATE_PASSWORD_BY_EMAIL);
            preparedStatement.setString(1, hashPassword);
            preparedStatement.setString(2, email);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error in updatePassword");
            throw new DaoException("UserDao error while updatePassword", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement);
        }
        return result == 1;
    }

    @Override
    public boolean updateProfile(Map<String, String> userDataMap) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        proxyConnection = DbConnectionPool.getInstance().takeConnection();
        int result = 0;
        try {
            preparedStatement =
                    proxyConnection.prepareStatement(SQL_UPDATE_PROFILE);
            preparedStatement.setString(1, userDataMap.get(FIRST_NAME));
            preparedStatement.setString(2, userDataMap.get(LAST_NAME));
            preparedStatement.setString(3, userDataMap.get(LOGIN));
            preparedStatement.setString(4, userDataMap.get(EMAIL));
            preparedStatement.setString(5, userDataMap.get(PHONE));
            preparedStatement.setInt(6, Integer.parseInt(userDataMap.get(ID)));
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error in updateProfile");
            throw new DaoException("UserDao error while updateProfile", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement);
        }
        return result == 1;
    }

    @Override
    public boolean updateMoneyAmount(int currentUserId, BigDecimal resultAmount) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        proxyConnection = DbConnectionPool.getInstance().takeConnection();
        int result = 0;
        try {
            preparedStatement =
                    proxyConnection.prepareStatement(SQL_UPDATE_MONEY_AMOUNT);
            preparedStatement.setDouble(1, resultAmount.doubleValue());
            preparedStatement.setInt(2, currentUserId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error in updateProfile");
            throw new DaoException("UserDao error while updateMoneyAmount", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement);
        }
        return result == 1;
    }

    @Override
    public Optional<User> findEntityByOneParam(String paramName, String paramValue) throws DaoException {
        if (paramName != null && !paramName.matches(PARAMETER_VALIDATOR_PATTERN)) {
            logger.log(Level.ERROR, "findUserByOneParam incorrect paramName");
            throw new DaoException("findUserByOneParam incorrect paramName");
        }
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<User> optionalUser = Optional.empty();
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(String.format(SQL_SELECT_USER_BY_VAR_PARAM, paramName));
            preparedStatement.setString(1, paramValue);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalUser = new UserMapper().mapEntity(resultSet);
                logger.log(Level.INFO, "user was found by param {} and param value {}", paramName, paramValue);
            }

        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error in findUserByOneParam");
            throw new DaoException("UserDao error while findUserByOneParam", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return optionalUser;
    }

    @Override
    public List<User> findAll() throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(SQL_FIND_ALL_USER_ORDER_BY_PARAM);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Optional<User> optionalUser = new UserMapper().mapEntity(resultSet);
                optionalUser.ifPresent(userList::add);
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findAll");
            throw new DaoException("UserDao error while findAll", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return userList;
    }

    @Override
    public Map<User, double[]> findAllUsersOrdersSum() throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Map<User, double[]> userMap = new LinkedHashMap<>();
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    proxyConnection.prepareStatement(SQL_FIND_ALL_USER_ORDER_SUM);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Optional<User> optionalUser = new UserMapper().mapEntity(resultSet);
                if (optionalUser.isPresent()) {
                    userMap.put(optionalUser.get(), new double[]
                            {resultSet.getDouble("number_prods"), resultSet.getDouble("total_ords_sum")});
                }
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findAllUsersOrdersSum");
            throw new DaoException("UserDao error while findAllUsersOrdersSum", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return userMap;
    }

    @Override
    public boolean findOrderByParam(List<Order> orderList, Map<String, String> incomeParam) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String id = incomeParam.getOrDefault(ID, FIND_ANY);
        String userId = incomeParam.getOrDefault(USER_ID, FIND_ANY);
        String orderStatus = incomeParam.getOrDefault(STATUS, FIND_ANY);
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_FIND_ORDER_BY_PARAM);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, orderStatus);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Optional<Order> optionalOrder = new OrderMapper().mapEntity(resultSet);
                optionalOrder.ifPresent(orderList::add);
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while  findOrderByParam");
            throw new DaoException("UserDao error while  findOrderByParam", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return true;
    }

    @Override
    public boolean findOrderProductMap(int orderId, Map<Integer, Integer> productIdQuantity) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_FIND_PRODUCT_QUANTITY_BY_ORDER_ID);
            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productIdQuantity.put(resultSet.getInt(PRODUCT_ID), resultSet.getInt(QUANTITY));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "error while findOrderProductMap");
            proxyConnection.setForChecking(true);
            throw new DaoException("UserDao error while findOrderProductMap", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return true;
    }

    @Override
    public boolean createOrderTransaction(User currentUser, Cart currentCart, Map<String, String> orderDataMap) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Map<Product, Integer> productQuantity = currentCart.getProductQuantity();
        int lastOrderId;
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            proxyConnection.setAutoCommit(false);
            //создать новый Order
            preparedStatement = proxyConnection.prepareStatement(SQL_CREATE_ORDER);
            preparedStatement.setInt(1, currentUser.getId());
            preparedStatement.setString(2, orderDataMap.get(ADDRESS));
            preparedStatement.setString(3, orderDataMap.get(PHONE));
            preparedStatement.setString(4, orderDataMap.get(DETAIL));
            preparedStatement.execute();
//            preparedStatement.close();
// отключено по требованию преподавателя
            //получить его id
            preparedStatement = proxyConnection.prepareStatement(SQL_FIND_LAST_ORDER_ID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            lastOrderId = resultSet.getInt(1);
//            preparedStatement.close();
//            resultSet.close();
// отключено по требованию преподавателя
            //пробежаться по мапе продуктов
            for (Map.Entry<Product, Integer> entry : productQuantity.entrySet()) {
                int productId = entry.getKey().getId();
                int quantity = entry.getValue();
                //скорректировть количество товара
                preparedStatement = proxyConnection.prepareStatement(SQL_CHANGE_PRODUCT_QUANTITY);
                preparedStatement.setInt(1, quantity);
                preparedStatement.setInt(2, productId);
                if (preparedStatement.executeUpdate() != 1) {//нужна эта проверка?
                    throw new SQLException("update product return quantity of updated rows != 1");
                }
                //создать промежуточную таблицу
                preparedStatement = proxyConnection.prepareStatement(SQL_CREATE_ORDER_PRODUCTS);
                preparedStatement.setInt(1, lastOrderId);
                preparedStatement.setInt(2, productId);
                preparedStatement.setInt(3, quantity);
                preparedStatement.execute();
//                preparedStatement.close();
// отключено по требованию преподавателя
            }
            //скорректировать количество денег у узера
            preparedStatement = proxyConnection.prepareStatement(SQL_CHANGE_USER_MONEY);
            preparedStatement.setBigDecimal(1, currentCart.getTotalCost());
            preparedStatement.setInt(2, currentUser.getId());
            if (preparedStatement.executeUpdate() != 1) {//нужна эта проверка?
                throw new SQLException("update user money return quantity of updated rows != 1");
            }
            proxyConnection.commit();
            return true;
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "SqlException while createOrderTransaction", e);
            throw new DaoException("SqlException while createOrderTransaction", e);
        } finally {
            try {
                if (proxyConnection != null) {
                    proxyConnection.rollback();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "SqlException while createOrderTransaction rollback", e);
            } finally {
                finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
            }
        }
    }

    @Override
    public boolean cancelOrderTransaction(Order order) throws DaoException {
        boolean isCorrect = true;
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BigDecimal totalCost = new BigDecimal(0);
        BigDecimal userAmount = new BigDecimal(0);
        int currentQuantity;
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            proxyConnection.setAutoCommit(false);
            //change status
            preparedStatement = proxyConnection.prepareStatement(SQL_UPDATE_ORDER_STATUS);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(3, order.getId());
            preparedStatement.setString(2, Order.Status.CANCELED.toString());
            if (preparedStatement.executeUpdate() != 1) {
                isCorrect = false;
                logger.log(Level.WARN, "order id {} was not found", order.getId());
            }
            ;
//            preparedStatement.close();
// отключено по требованию преподавателя
            //return product to stock
            Map<Integer, Integer> prodIdQuantity = order.getProdIdQuantity();
            for (Map.Entry<Integer, Integer> entry : prodIdQuantity.entrySet()) {
                preparedStatement = proxyConnection.prepareStatement(SQL_FIND_PRODUCT_BY_ID, ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
                preparedStatement.setInt(1, entry.getKey());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    totalCost = totalCost.add(resultSet.getBigDecimal(PRICE).multiply(new BigDecimal(entry.getValue())));
                    currentQuantity = resultSet.getInt(QUANTITY_IN_STOCK);
                    resultSet.updateInt(QUANTITY_IN_STOCK, currentQuantity + entry.getValue());
                    resultSet.updateRow();
                } else {
                    isCorrect = false;
                    logger.log(Level.ERROR, "product wasn't found");
                }
//                resultSet.close();
//                preparedStatement.close();
// отключено по требованию преподавателя
            }
            //return user's money
            preparedStatement =
                    proxyConnection.prepareStatement(String.format(SQL_SELECT_USER_BY_VAR_PARAM, ID), ResultSet.TYPE_FORWARD_ONLY,
                            ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setInt(1, order.getUserId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userAmount = resultSet.getBigDecimal(MONEY_AMOUNT);
                resultSet.updateBigDecimal(MONEY_AMOUNT, userAmount.add(totalCost));
                resultSet.updateRow();
            } else {
                isCorrect = false;
                logger.log(Level.ERROR, "user wasn't found");
            }
            proxyConnection.commit();
            return isCorrect;
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "SqlException while createOrderTransaction", e);
            throw new DaoException("SqlException while createOrderTransaction", e);
        } finally {
            try {
                if (proxyConnection != null) {
                    proxyConnection.rollback();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "SqlException while createOrderTransaction rollback", e);
            } finally {
                finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
            }
        }
    }

    @Override
    public boolean deliveryOrder(Order order) throws DaoException {
        boolean isCorrect = true;
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_UPDATE_ORDER_STATUS);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(3, order.getId());
            preparedStatement.setString(2, Order.Status.DELIVERED.toString());
            if (preparedStatement.executeUpdate() != 1) {
                isCorrect = false;
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while deliveryOrder");
            throw new DaoException("UserDao error while deliveryOrder", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement);
        }
        return isCorrect;
    }

    @Override
    public boolean findOrderMapByParam(Map<Order, String[]> orderMap, Map<String, String> incomeParam) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String id = incomeParam.getOrDefault(ID, FIND_ANY);
        String userId = incomeParam.getOrDefault(USER_ID, FIND_ANY);
        String orderStatus = incomeParam.getOrDefault(STATUS, FIND_ANY);
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_FIND_ORDER_SUM_BY_PARAM);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, orderStatus);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Optional<Order> optionalOrder = new OrderMapper().mapEntity(resultSet);
                if (optionalOrder.isPresent()) {
                    orderMap.put(optionalOrder.get(), new String[]{
                            resultSet.getString(LOGIN),
                            resultSet.getString(TOTAL_SUM)
                    });
                }
            }
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while findOrderMapByParam");
            throw new DaoException("UserDao error while findOrderMapByParam", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement, resultSet);
        }
        return true;
    }


    //TODO override methods!!!!!!!!!!!

    @Override
    public boolean delete(int id) throws DaoException {
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        int rowsDeleted;
        try {
            proxyConnection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = proxyConnection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setInt(1, id);
            rowsDeleted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            proxyConnection.setForChecking(true);
            logger.log(Level.ERROR, "error while delete");
            throw new DaoException("UserDao error while delete", e);
        } finally {
            finallyWhileClosing(proxyConnection, preparedStatement);
        }
        if (rowsDeleted < 1) {
            logger.log(Level.ERROR, "no user with id: {} were deleted", id);
        }
        return rowsDeleted > 0;
    }

}
