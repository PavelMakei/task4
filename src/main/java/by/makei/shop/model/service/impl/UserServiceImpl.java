package by.makei.shop.model.service.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.dao.BaseDao;
import by.makei.shop.model.dao.ProductDao;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.impl.ProductDaoImpl;
import by.makei.shop.model.dao.impl.UserDaoImpl;
import by.makei.shop.model.entity.Cart;
import by.makei.shop.model.entity.Order;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.validator.AttributeValidator;
import by.makei.shop.model.validator.ParameterValidator;
import by.makei.shop.model.validator.impl.AttributeValidatorImpl;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import by.makei.shop.util.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

import static by.makei.shop.command.AttributeName.*;
import static by.makei.shop.command.RedirectMessage.ORDERING_FAIL_INCORRECT_DATA;
import static by.makei.shop.command.RedirectMessage.ORDERING_FAIL_NOT_ENOUGH_PRODUCTS;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    public static UserServiceImpl instance;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    /**
     * find user by login and password
     * @param login as String
     * @param password as String
     * @return optional
     * @throws ServiceException
     */
    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        AttributeValidator validator = AttributeValidatorImpl.getInstance();
        if (!validator.isLoginValid(login) || !validator.isPasswordValid(password)) {
            logger.log(Level.INFO, "user -{}- or password wasn't found", login);
            return Optional.empty();
        }
        UserDao userDao = UserDaoImpl.getInstance();
        String hashPassword = PasswordEncoder.getHashedPassword(password);
        try {
            return userDao.findUserByLoginAndPassword(login, hashPassword);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while signIn in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * check input params and if correct create user, else add incorrect markers into userData map
      * @param userData Map with income data
     * @param session
     * @return if input data incorrect - false
     * @throws ServiceException
     */
    @Override
    public boolean createUser(Map<String, String> userData, HttpSession session) throws ServiceException {
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        try {
            if (parameterValidator.validateAndMarkIncomeData(userData)
                && parameterValidator.validateAndMarkIfLoginCorrectAndNotExistsInDb(userData)
                && parameterValidator.validateAndMarkIfPhoneCorrectAndNotExistsInDb(userData)
                && parameterValidator.validateAndMarkIfEmailCorrectAndNotExistsInDb(userData)
                && parameterValidator.validateAndMarkActivationCodeAndSavedEmail(userData, session)) {

                User user = new User();
                String hashPassword;

                user.setFirstName(userData.get(FIRST_NAME));
                user.setLastName(userData.get(LAST_NAME));
                user.setLogin(userData.get(LOGIN));
                user.setEmail(userData.get(EMAIL));
                user.setPhone(userData.get(PHONE));
                hashPassword = PasswordEncoder.getHashedPassword(userData.get(PASSWORD));
                UserDaoImpl userDao = UserDaoImpl.getInstance();
                userDao.create(user, hashPassword);
                return true;

            } else {
                logger.log(Level.ERROR, "createUser incorrect data unput");
                return false;
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while addNewUser in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * find all users
     * @return List of user
     * @throws ServiceException
     */
    @Override
    public List<User> findAllUser() throws ServiceException {
        List<User> userList;
        BaseDao<User> userDao = UserDaoImpl.getInstance();
        try {
            userList = userDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while findAllUser in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return userList;
    }

    /**
     * find all users, quantity of products and total price sum in {@link Order} with status 'DELIVERED'
     * @return Map <{@link User}, double[0] - quantity of bought products, double[1] - total price sum
     * @throws ServiceException
     */
    @Override
    public Map<User, double[]> findAllUserOrderSum() throws ServiceException {
        Map<User, double[]> userOrderMap;
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            userOrderMap = userDao.findAllUsersOrdersSum();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while findAllUser in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return userOrderMap;
    }

    /**
     * check if income params are correct and if correct - try to update AccessLevel and return result as boolean
     * else mark incorrect data and return false
     * @param userDataMap
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean updateAccessLevel(Map<String, String> userDataMap) throws ServiceException {
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        if (!parameterValidator.validateAndMarkIncomeData(userDataMap)) {
            return false;
        }

        UserDao userDao = UserDaoImpl.getInstance();
        try {
            return userDao.updateAccessLevel(userDataMap);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while updateAccessLevel in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * find user by email
     * @param email as String
     * @return Optional {@link User}
     * @throws ServiceException
     */
    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            optionalUser = userDao.findEntityByOneParam(EMAIL, email);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while findUserByEmail in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return optionalUser;
    }

    /**
     * encoding password to hashedPassword {@link PasswordEncoder} and try to update it by email {@link User}
     * @param userDataMap
     * @return boolean result
     * @throws ServiceException
     */
    @Override
    public boolean updatePassword(Map<String, String> userDataMap) throws ServiceException {
        boolean isCorrect = false;
        UserDao userDao = UserDaoImpl.getInstance();
        String email = userDataMap.get(EMAIL);
        String password = userDataMap.get(PASSWORD);
        String hashPassword = PasswordEncoder.getHashedPassword(password);
        try {
            isCorrect = userDao.updatePassword(email, hashPassword);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while updatePassword in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return isCorrect;
    }

    /**
     * try to update {@link User} profile
     * @param userDataMap
     * @return boolean result
     * @throws ServiceException
     */
    @Override
    public boolean updateProfile(Map<String, String> userDataMap) throws ServiceException {
        boolean isCorrect = false;
        UserDao userDao = UserDaoImpl.getInstance();

        try {
            isCorrect = userDao.updateProfile(userDataMap);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while updateProfile in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return isCorrect;
    }

    /**
     * find {@link User} by one parameter (ID, EMAIL, LOGIN...) if number of user have the same pair param - value, returns first found
     * @param paramName
     * @param paramValue
     * @return Optional {@link User}
     * @throws ServiceException
     */
    @Override
    public Optional<User> findUserByOneParam(String paramName, String paramValue) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            return userDao.findEntityByOneParam(paramName, paramValue);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while findUserByOneParam in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * update user's money amount with sum of currentUserAmount and amountToDeposit
     * @param currentUserId as int
     * @param currentUserAmount as BigDecimal
     * @param amountToDeposit as String
     * @return boolean result
     * @throws ServiceException
     */
    @Override
    public boolean updateUserMoneyAmount(int currentUserId, BigDecimal currentUserAmount, String amountToDeposit) throws
            ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        BigDecimal inputAmount = BigDecimal.valueOf(Double.valueOf(amountToDeposit));
        BigDecimal resultAmount = currentUserAmount.add(inputAmount);
        try {
            return userDao.updateMoneyAmount(currentUserId, resultAmount);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while updateAmount in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * check if input data are not correct add MESSAGE ORDERING_FAIL_INCORRECT_DATA to orderDataMap, return false
     * check if user has enough money to make this transaction, if not - add MESSAGE ORDERING_FAIL_NOT_ENOUGH_PRODUCTS to orderDataMap, return false
     * try to make transaction and return boolean result
     * @param currentUser as {@link User}
     * @param currentCart as currentUser {@link Cart}
     * @param orderDataMap
     * @return boolean as result
     * @throws ServiceException
     */
    @Override
    public boolean createOrder(User currentUser, Cart currentCart, Map<String, String> orderDataMap) throws
            ServiceException {
        ParameterValidator validator = ParameterValidatorImpl.getInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            if (!validator.validateAndMarkIncomeData(orderDataMap)) {
                orderDataMap.put(MESSAGE, ORDERING_FAIL_INCORRECT_DATA);
                return false;
            }
            if (!isStillEnoughProductInStock(currentCart)) {
                orderDataMap.put(MESSAGE, ORDERING_FAIL_NOT_ENOUGH_PRODUCTS);
                return false;
            }
            userDao.createOrderTransaction(currentUser, currentCart, orderDataMap);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while create order", e);
            throw new ServiceException(e);
        }
        return true;
    }

    /**
     * check if quantity of ordered (in {@link Cart}) products <= quantity these products in stock
     * if not - correct quantity in currentCart = quantity in stock. If quantity in stock == 0< remove this product from currentCart and return false
     * @param currentCart
     * @return boolean as result
     * @throws ServiceException
     */
    private boolean isStillEnoughProductInStock(Cart currentCart) throws ServiceException {
        boolean isEnough = true;
        ProductDao productDao = ProductDaoImpl.getInstance();
        Map<Product, Integer> productQuantity = currentCart.getProductQuantity();
        try {
            for (Map.Entry<Product, Integer> entry : productQuantity.entrySet()) {
                Map<Product, String> currentProductQuantity = new HashMap<>();
                productDao.findMapProductQuantityById(ID, String.valueOf(entry.getKey().getId()), currentProductQuantity);
                if (currentProductQuantity.size() == 0) {
                    currentCart.removeProduct(entry.getKey(), entry.getValue());
                    isEnough = false;
                } else {
                    int difference = entry.getValue() - Integer.parseInt(currentProductQuantity.get(entry.getKey()));
                    if (difference > 0) {
                        currentCart.removeProduct(entry.getKey(), difference);
                        isEnough = false;
                    }
                }
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while isStillEnoughProductInStock", e);
            throw new ServiceException(e);
        }
        return isEnough;
    }


    @Override
    public boolean validateAndMarkIncomeData(Map<String, String> incomeDataMap) throws ServiceException {
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        try {
            return parameterValidator.validateAndMarkIncomeData(incomeDataMap);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while validateAndMarkIncomeData", e);
            throw new ServiceException(e);
        }
    }

    /**
     * check if input parameters are valid. If not - mark incorrect and return false.
     * find orders by param and fill them into orderList
     * @param orderList {@link Order} as List
     * @param incomeParam (orders.ID, USER_ID,STATUS) if there is no parameter - will be used % to find all
     * @return boolean as result
     * @throws ServiceException
     */
    @Override
    public boolean findOrderByParam(List<Order> orderList, Map<String, String> incomeParam) throws ServiceException {
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        int orderId;
        Map<Integer, Integer> productIdQuantity;
        if (!parameterValidator.validateAndMarkIncomeData(incomeParam)) {
            logger.log(Level.ERROR, "findOrderByParam incorrect data input");
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        //find list of orders
        try {
            userDao.findOrderByParam(orderList, incomeParam);
            //run through list
            for (Order order : orderList) {
                orderId = order.getId();
                //find product-quantity
                productIdQuantity = order.getProdIdQuantity();
                userDao.findOrderProductMap(orderId, productIdQuantity);
                //fill orders with order-quantity
                order.setProdIdQuantity(productIdQuantity);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while findOrderByParam", e);
            throw new ServiceException(e);
        }
        return true;
    }

    /**
     *
     * @param orderMap empty Orders as key and String[] as value
     *                 String[0] - User.login, String[1] - total sum of order
     * @param incomeParam - Product.id, User.id, Product.status. If is empty will be found all
     * @return filled income orderMap
     * @throws ServiceException
     */
    @Override
    public boolean findOrderMapByParam(Map<Order, String[]> orderMap, Map<String, String> incomeParam) throws ServiceException {
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        int orderId;
        Map<Integer, Integer> productIdQuantity;
        if (!parameterValidator.validateAndMarkIncomeData(incomeParam)) {
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        //find map of orders
        try {
            userDao.findOrderMapByParam(orderMap, incomeParam);
            //run through map
            for (Map.Entry<Order,String[]> entry: orderMap.entrySet()) {
                orderId = entry.getKey().getId();
                //find product-quantity
                productIdQuantity = entry.getKey().getProdIdQuantity();
                userDao.findOrderProductMap(orderId, productIdQuantity);
                //fill orders with order-quantity
                entry.getKey().setProdIdQuantity(productIdQuantity);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while findOrderByParam", e);
            throw new ServiceException(e);
        }
        return true;
    }

    /**
     * check if input params are correct. If not - mark incorrect, return false.
     * try to make cancel transaction, return result
     * @param request should consist ID
     * @return boolean as result
     * @throws ServiceException
     */
    @Override
    public boolean cancelOrder(HttpServletRequest request) throws ServiceException {
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        Map<String, String> incomeParam = new HashMap<>();
        UserDao userDao = UserDaoImpl.getInstance();
        //grab ID
        String id = request.getParameter(ID);
        incomeParam.put(ID, id);
        try {
            if (!parameterValidator.validateAndMarkIncomeData(incomeParam)) {
                logger.log(Level.ERROR, "invalid Id {}", id == null ? "null" : id);
                return false;
            }
            //if valid - create order?
            //Fill prodIdQuantity map to order / just create?
            List<Order> orderList = new ArrayList<>();
            findOrderByParam(orderList, incomeParam);
            Order order = orderList.get(0);
            if(!order.getStatus().equals(Order.Status.PAID)){
                logger.log(Level.ERROR, "cancelOrderTransaction not allowed order status for transaction: {}"
                        , order.getStatus());
                return false;
            }
            if (!userDao.cancelOrderTransaction(order)) {
                logger.log(Level.ERROR, "error while cancelOrderTransaction");
                return false;
            }
        }
        catch (DaoException e) {
            logger.log(Level.ERROR, "exception while cancelOrderTransaction");
            throw new ServiceException(e);
        }
        return true;
    }

    /**
     * check if input parameter is correct. If not - return false.
     * try to change order status, return result
     * @param request should consist ID ({@link Order})
     * @return boolean as result
     * @throws ServiceException
     */
    @Override
    public boolean deliveryOrder(HttpServletRequest request) throws ServiceException {
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        Map<String, String> incomeParam = new HashMap<>();
        UserDao userDao = UserDaoImpl.getInstance();
        String id = request.getParameter(ID);
        incomeParam.put(ID, id);
        try {
            if (!parameterValidator.validateAndMarkIncomeData(incomeParam)) {
                logger.log(Level.ERROR, "invalid Id {}", id == null ? "null" : id);
                return false;
            }
            List<Order> orderList = new ArrayList<>();
            findOrderByParam(orderList, incomeParam);
            Order order = orderList.get(0);
            if(!order.getStatus().equals(Order.Status.PAID)){
                logger.log(Level.ERROR, "deliverOrder not allowed order status for transaction: {}"
                        , order.getStatus());
                return false;
            }
            if (!userDao.deliveryOrder(order)) {
                logger.log(Level.ERROR, "error while UserDao deliverOrder");
                return false;
            }
        }
        catch (DaoException e) {
            logger.log(Level.ERROR, "exception while deliverOrder");
            throw new ServiceException(e);
        }
        return true;
    }
}
