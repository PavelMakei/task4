package by.makei.shop.model.dao.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.dao.AbstractDao;
import by.makei.shop.model.dao.UserDao;
import by.makei.shop.model.dao.builder.UserBuilder;
import by.makei.shop.model.entity.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD = """
            SELECT id, first_name, last_name, login, password, email, phone, access_level, registration_date,money_amount
            FROM lightingshop.users WHERE
            login = ?
            AND
            password = ?""";

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findEntityById(int id) {
        return null;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public Optional<User> selectUserByLoginAndPassword(String login, String password) throws DaoException {
        Optional<User> optionalUser = Optional.empty();

        try (Connection connection = DbConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalUser = new UserBuilder().mapUser(resultSet);
                }

            } catch (SQLException ex) {
                //TODO logger
                throw new DaoException(ex);
            }
            return optionalUser;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalUser;
    }

}
