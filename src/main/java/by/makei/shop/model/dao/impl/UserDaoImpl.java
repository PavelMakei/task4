package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.connectionpool.ProxyConnection;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.mapper.impl.UserMapper;
import by.makei.shop.model.entity.User;
import org.apache.logging.log4j.Level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.*;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance = new UserDaoImpl();
    public static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD = """
            SELECT id, first_name, last_name, login, password, email, phone, access_level, registration_date,money_amount
            FROM lightingshop.users WHERE
            login = ?
            AND
            password = ?""";
    public static final String SQL_ADD_NEW_USER = """
            INSERT INTO lightingshop.users(first_name, last_name, login, password, email, phone)
            VALUES(?,?,?,?,?,?)""";
    public static final String SQL_SELECT_USER_BY_VAR_PARAM = """
            SELECT id, first_name, last_name, login, password, email, phone, access_level, registration_date,money_amount
            FROM lightingshop.users WHERE %s = ?
            """;
    public static final String SQL_FIND_ALL_USER_ORDER_BY_PARAM = """
            SELECT id, first_name, last_name, login, password, email, phone, access_level, registration_date,money_amount FROM lightingshop.users
            ORDER BY last_name
            """;
    public static final String SQL_UPDATE_ACCESS_LEVEL = """
            UPDATE lightingshop.users
            SET access_level =?
            WHERE id =?
            """;
    public static final String SQL_UPDATE_PASSWORD_BY_EMAIL = """
            UPDATE lightingshop.users
            SET password =?
            WHERE email =?
            """;
    public static final String SQL_UPDATE_PROFILE = """
            UPDATE lightingshop.users
            SET first_name =?, last_name =?, login =?, email =?, phone =?
            WHERE id =?
            """;

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

    //TODO override methods!!!!!!!!!!!
    @Override
    public boolean delete(User entity) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(User entity) throws DaoException {
        return false;
    }

    @Override
    public User update(User entity) throws DaoException {
        return null;
    }

}
