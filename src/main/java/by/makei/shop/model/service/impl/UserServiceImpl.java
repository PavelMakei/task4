package by.makei.shop.model.service.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.exception.ServiceException;
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
        UserDao userDao = new UserDaoImpl();
        String hashPassword = PasswordEncoder.getHashedPassword(password);
        try {
            return userDao.findUserByLoginAndPassword(login, hashPassword);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while signIn in UserService. {}",e.getMessage());
            throw new ServiceException(e);
        }
    }

    public boolean addNewUser(Map<String, String> userData) throws ServiceException {
        User user = new User();
        String hashPassword;

        user.setFirstName(userData.get(FIRST_NAME));
        user.setLastName(userData.get(LAST_NAME));
        user.setLogin(userData.get(LOGIN.toLowerCase()));
        user.setEmail(userData.get(EMAIL.toLowerCase()));
        user.setPhone(userData.get(PHONE));

        hashPassword = PasswordEncoder.getHashedPassword(userData.get(PASSWORD));

        UserDaoImpl userDao = new UserDaoImpl();
        try {
            userDao.create(user, hashPassword);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "error while addNewUser in UserService. {}",e.getMessage());
            throw new ServiceException(e);
        }
        return true;
    }


}
