package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.entity.User;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static by.makei.shop.command.AttributeName.LOGIN;

class UserDaoImplTest {
    static final String USER_LOGIN = "admin";
    public static UserDao userDao;

    @BeforeAll
    static void init() {
        userDao = UserDaoImpl.getInstance();;
    }

    @Test
    void findUserByByOneParamTest() throws DaoException {
        Optional<User> actual = userDao.findEntityByOneParam(LOGIN,USER_LOGIN);
        assert(!actual.isEmpty());
    }

    @AfterAll
    static void teardown(){
        DbConnectionPool.getInstance().shutdown();
    }
}