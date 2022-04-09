package by.makei.shop.model.service.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.impl.UserDaoImpl;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance;

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
}
