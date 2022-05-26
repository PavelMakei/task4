package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserDao extends BaseDao<User>{

    Optional<User> findUserByLoginAndPassword(String login, String hashPassword) throws DaoException;

    boolean create(User user, String hashPassword) throws DaoException;


    boolean updateAccessLevel(Map<String, String> userDataMap) throws DaoException;

    boolean updatePassword(String email, String hashPassword) throws DaoException;

    boolean updateProfile(Map<String, String> userDataMap) throws DaoException;
}
