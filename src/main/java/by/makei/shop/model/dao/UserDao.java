package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao{

    Optional<User> findUserByLoginAndPassword(String login, String hashPassword) throws DaoException;

    boolean create(User user, String hashPassword) throws DaoException;

    Optional<User> findUserByOneParam(String paramName, String paramValue) throws DaoException;

    boolean delete(User entity);

    boolean create(User entity);


}
