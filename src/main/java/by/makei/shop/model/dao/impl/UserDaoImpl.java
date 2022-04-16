package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.mapper.UserMapper;
import by.makei.shop.model.entity.AbstractEntity;
import by.makei.shop.model.entity.User;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

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

        try (Connection connection = DbConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
//TODO if it needs?            connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, hashPassword);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalUser = new UserMapper().mapEntity(resultSet);
                    logger.log(Level.INFO, "user was found by login {} and hash password {}", login, hashPassword);
                }
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "error in findUserByLoginAndPassword");
                throw new DaoException(ex);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "error in findUserByLoginAndPassword");
            throw new DaoException(e);
        }
        return optionalUser;
    }

    @Override
    public boolean create(User user, String hashPassword) throws DaoException {

        try (Connection connection = DbConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_NEW_USER)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, hashPassword);
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());

            preparedStatement.execute();
            logger.log(Level.INFO, "new user was created. \n---- {}", user);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "new user wasn't created." + e.getMessage());
            throw new DaoException(e);
        }
        //connection close automatically by close methods and try(resources). Connection - override in proxyConnection, statement and resultset in baseDao
        return true;
    }

    @Override
    public Optional<User> findUserByOneParam(String paramName, String paramValue) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        String fullQuery = SQL_SELECT_USER_BY_VAR_PARAM + "WHERE " + paramName + " = ?";
        try (Connection connection = DbConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(String.format(SQL_SELECT_USER_BY_VAR_PARAM, paramName)))
        {
            preparedStatement.setString(1, paramValue);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalUser = new UserMapper().mapEntity(resultSet);
                    logger.log(Level.INFO, "user was found by param {} and param value {}",paramName, paramValue );
                }
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "error in findUserByOneParam");
                throw new DaoException(ex);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "error in findUserByOneParam");
            throw new DaoException(e);
        }
        return optionalUser;
    }


    //TODO override methods!!!!!!!!!!!
    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public List findAll() throws DaoException {
        return null;
    }

    @Override
    public AbstractEntity findEntityById(int id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(AbstractEntity entity) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(AbstractEntity entity) throws DaoException {
        return false;
    }

    @Override
    public AbstractEntity update(AbstractEntity entity) throws DaoException {
        return null;
    }

}
