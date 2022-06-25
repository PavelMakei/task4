package by.makei.shop.model.service.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.impl.UserDaoImpl;
import by.makei.shop.model.entity.Order;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.service.UserService;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.util.*;

import static by.makei.shop.model.entity.AccessLevel.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    public UserDao userDaoMock;
    public UserService userService;
    private User user;

//    @BeforeEach
//    public void initialize(){
//        user = new User(1, "Name", "Surname", "Admin", "admin@ya.ru", "1234567", ADMIN, new Date(), new BigDecimal(100));
//        userService = UserServiceImpl.getInstance();
//        userDaoMock = Mockito.mock(UserDaoImpl.class);
//        setMock(userDaoMock);
//
//    }
////TODO doesn't work
//
//
//    @Test
//    void signIn_Should_return_True() throws DaoException, ServiceException {
//        Mockito.when(userDaoMock.findUserByLoginAndPassword("Admin", "123456")).thenReturn(Optional.of(user));
//        Optional<User> actual = userService.signIn("Admin", "123456");
//        Optional<User> expected = Optional.of(user);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void addNewUser() {
//    }
//
//    private void setMock(UserDao mock) {
//        try {
//            Field userDao = UserServiceImpl.class.getDeclaredField("userDao");
//            userDao.setAccessible(true);
//            userDao.set(userService, mock);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Test
    void findOrderByParamTestShouldReturnFilledOrder() throws ServiceException {
        DbConnectionPool connectionPool = DbConnectionPool.getInstance();
        UserServiceImpl service = UserServiceImpl.getInstance();
        List<Order> orderList = new ArrayList<>();
        Map<String, String> incomeParam = new HashMap<>();
        service.findOrderByParam(orderList,incomeParam);
        assertTrue(orderList.size()>0);
        assertTrue(orderList.get(0).getProdIdQuantity().size()>0);
        connectionPool.shutdown();
    }

    @Test
    void findOrderMapByParamShouldReturnFilledOrder() throws ServiceException {
        DbConnectionPool connectionPool = DbConnectionPool.getInstance();
        UserServiceImpl service = UserServiceImpl.getInstance();
        Map<Order, String[]> orderMap = new LinkedHashMap<>();
        Map<String, String> incomeParam = new HashMap<>();
        service.findOrderMapByParam(orderMap,incomeParam);
        Map.Entry<Order, String[]> entry = orderMap.entrySet().iterator().next();
        assertTrue(orderMap.size()>0);
        assertTrue(entry.getKey().getProdIdQuantity().size()>0);
        connectionPool.shutdown();

    }
}