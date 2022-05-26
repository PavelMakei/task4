package by.makei.shop.model.service.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.dao.BaseDao;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.impl.UserDaoImpl;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.validator.AttributeValidator;
import by.makei.shop.model.validator.impl.AttributeValidatorImpl;
import by.makei.shop.util.PasswordEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.*;

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
    public boolean createUser(Map<String, String> userData) throws ServiceException {
        User user = new User();
        String hashPassword;

        user.setFirstName(userData.get(FIRST_NAME));
        user.setLastName(userData.get(LAST_NAME));
        user.setLogin(userData.get(LOGIN));
        user.setEmail(userData.get(EMAIL));
        user.setPhone(userData.get(PHONE));

        hashPassword = PasswordEncoder.getHashedPassword(userData.get(PASSWORD));

        UserDaoImpl userDao = UserDaoImpl.getInstance();
        ;
        try {
            userDao.create(user, hashPassword);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while addNewUser in UserService. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return true;
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
            optionalUser = userDao.findEntityByOneParam(EMAIL,email);
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
        try{
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

        try{
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


}
