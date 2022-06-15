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

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.RedirectMessage.ORDERING_FAIL_INCORRECT_DATA;
import static by.makei.shop.model.command.RedirectMessage.ORDERING_FAIL_NOT_ENOUGH_PRODUCTS;

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
                return false;
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while addNewUser in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

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
            if (!isSteelEnoughProductInStock(currentCart)) {
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

    private boolean isSteelEnoughProductInStock(Cart currentCart) throws ServiceException {
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
            logger.log(Level.ERROR, "error while isSteelEnoughProductInStock", e);
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

    @Override
    public boolean findOrderByParam(List<Order> orderList, Map<String, String> incomeParam) throws ServiceException {
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        int orderId;
        Map<Integer, Integer> productIdQuantity;
        if (!parameterValidator.validateAndMarkIncomeData(incomeParam)) {
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        //получить лист ордеров
        try {
            userDao.findOrderByParam(orderList, incomeParam);
            //пройти по списку
            for (Order order : orderList) {
                orderId = order.getId();

                //получить мапу продукт-количество для каждого
                productIdQuantity = order.getProdIdQuantity();
                userDao.findOrderProductMap(orderId, productIdQuantity);
                //добавить в каждый его мапу
                order.setProdIdQuantity(productIdQuantity);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while findOrderByParam", e);
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public boolean cancelOrder(HttpServletRequest request) throws ServiceException {
        ParameterValidatorImpl parameterValidator = ParameterValidatorImpl.getInstance();
        Map<String, String> incomeParam = new HashMap<>();
        UserDao userDao = UserDaoImpl.getInstance();
        //grab ID
        String id = request.getParameter(ID);
        incomeParam.put(ID, id);

        try {
            //Validate map
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
