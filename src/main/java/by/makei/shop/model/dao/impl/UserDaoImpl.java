package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.connectionpool.ProxyConnection;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.mapper.impl.UserMapper;
import by.makei.shop.model.entity.User;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

//    private static final String PARAMETER_VALIDATOR_PATTERN = "[a-z_]+";

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


    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String hashPassword) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        ProxyConnection proxyConnection = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD);
            proxyConnection = (ProxyConnection) connection;
            preparedStatement.setString(1, login.toLowerCase());
            preparedStatement.setString(2, hashPassword);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalUser = new UserMapper().mapEntity(resultSet);
                logger.log(Level.INFO, "user was found by login {} and hash password {}", login, hashPassword);
            }
        } catch (SQLException e) {
            if (proxyConnection != null) {
                proxyConnection.setForChecking(true);
            }
            logger.log(Level.ERROR, "error in findUserByLoginAndPassword");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(connection, preparedStatement, resultSet);
        }
        return optionalUser;
    }

    @Override
    public boolean create(User user, String hashPassword) throws DaoException {
        Connection connection = null;
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_NEW_USER);
            proxyConnection = (ProxyConnection) connection;
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, hashPassword);
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.execute();
            logger.log(Level.INFO, "new user was created. \n---- {}", user);
        } catch (SQLException e) {
            if (proxyConnection != null) {
                proxyConnection.setForChecking(true);
            }
            logger.log(Level.ERROR, "UserDao error while create. {}", e.getMessage());
            throw new DaoException("UserDao error while create",e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "UserDao create. Error while closing resources. {}", e.getMessage());
            }
        }
        return true;
    }

    @Override
    public Optional<User> findEntityByOneParam(String paramName, String paramValue) throws DaoException {
        if (paramName != null && !paramName.matches(PARAMETER_VALIDATOR_PATTERN)) {
            logger.log(Level.ERROR, "findUserByOneParam incorrect paramName");
            throw new DaoException("findUserByOneParam incorrect paramName");
        }
        Connection connection = null;
        ProxyConnection proxyConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<User> optionalUser = Optional.empty();
        String fullQuery = SQL_SELECT_USER_BY_VAR_PARAM + "WHERE " + paramName + " = ?";
        try {
            connection = DbConnectionPool.getInstance().takeConnection();
            preparedStatement =
                    connection.prepareStatement(String.format(SQL_SELECT_USER_BY_VAR_PARAM, paramName));
            proxyConnection = (ProxyConnection) connection;
            preparedStatement.setString(1, paramValue);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalUser = new UserMapper().mapEntity(resultSet);
                logger.log(Level.INFO, "user was found by param {} and param value {}", paramName, paramValue);
            }

        } catch (SQLException e) {
            if (proxyConnection != null) {
                proxyConnection.setForChecking(true);
            }
            logger.log(Level.ERROR, "error in findUserByOneParam");
            throw new DaoException(e);
        } finally {
            finallyWhileClosing(connection, preparedStatement, resultSet);
        }
        return optionalUser;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
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
