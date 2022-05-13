package by.makei.shop.model.service.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.impl.UserDaoImpl;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static by.makei.shop.model.entity.AccessLevel.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    public UserDao userDaoMock;
    public UserService userService;
    private User user;

    @BeforeEach
    public void initialize(){
        user = new User(1, "Name", "Surname", "Admin", "admin@ya.ru", "1234567", ADMIN, new Date(), new BigDecimal(100));
        userService = UserServiceImpl.getInstance();
        userDaoMock = Mockito.mock(UserDaoImpl.class);
        setMock(userDaoMock);

    }
//TODO doesn't work


    @Test
    void signIn_Should_return_True() throws DaoException, ServiceException {
        Mockito.when(userDaoMock.findUserByLoginAndPassword("Admin", "123456")).thenReturn(Optional.of(user));
        Optional<User> actual = userService.signIn("Admin", "123456");
        Optional<User> expected = Optional.of(user);
        assertEquals(expected, actual);
    }

    @Test
    void addNewUser() {
    }

    private void setMock(UserDao mock) {
        try {
            Field userDao = UserServiceImpl.class.getDeclaredField("userDao");
            userDao.setAccessible(true);
            userDao.set(userService, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}