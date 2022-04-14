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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static by.makei.shop.model.command.AttributeName.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance;

    private UserServiceImpl(){}

    public static UserServiceImpl getInstance() {
        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        //TODO validation
        //TODO encrypt password
        //TODO transaction???
        try {
            return userDao.selectUserByLoginAndPassword(login,password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public boolean validateUserData (Map<String,String> userData){
        Map<String,String> invalidParameters = new HashMap<>();
        AttributeValidator validator = AttributeValidatorImpl.getInstance();
        boolean isCorrect = true;

        for (Map.Entry<String, String> entry : userData.entrySet()) {
            String key = entry.getKey();
            switch (key) {
                case FIRST_NAME -> {
                    if (!validator.isNameValid(entry.getValue())) {
                        invalidParameters.put(INVALID_FIRST_NAME,INVALID_FIRST_NAME);
                        isCorrect = false;
                    }
                }
                case LAST_NAME -> {
                    if (!validator.isNameValid(entry.getValue())) {
                        invalidParameters.put(INVALID_LAST_NAME, INVALID_LAST_NAME);
                        isCorrect = false;
                    }
                }
                case LOGIN -> {
                    if (!validator.isLoginValid(entry.getValue())) {
                        invalidParameters.put(INVALID_LOGIN, INVALID_LOGIN);
                        isCorrect = false;
                        //TODO if busy?
                    }
                }
                case EMAIL -> {
                    if (!validator.isEmailValid(entry.getValue())) {
                        invalidParameters.put(INVALID_EMAIL,INVALID_EMAIL);
                        isCorrect = false;
                        //TODO if busy
                    }
                }
                case PHONE -> {
                    if (!validator.isPhoneValid(entry.getValue())) {
                        invalidParameters.put(INVALID_PHONE,INVALID_PHONE);
                        isCorrect = false;
                        //TODO if busy
                    }
                }
                case PASSWORD -> {
                    if (!validator.isPasswordValid(entry.getValue())) {
                        invalidParameters.put(INVALID_PASSWORD,INVALID_PASSWORD);
                        isCorrect = false;
                    }
                }
            }
        }
        userData.putAll(invalidParameters);
                return isCorrect;
    }

    public boolean addNewUser (Map<String, String> userData) throws ServiceException {
        User user = new User();
        String hashPassword;

        user.setFirstName(userData.get(FIRST_NAME));
        user.setLastName(userData.get(LAST_NAME));
        user.setLogin(userData.get(LOGIN));
        user.setEmail(userData.get(EMAIL));
        user.setPhone(userData.get(PHONE));

        hashPassword = PasswordEncoder.getHashedPassword(userData.get(PASSWORD));

        UserDaoImpl userDao = new UserDaoImpl();
        try {
            userDao.create(user, hashPassword);
        } catch (DaoException e) {
           //TODO
            throw new ServiceException(e);
        }
        return true;
    }
}
