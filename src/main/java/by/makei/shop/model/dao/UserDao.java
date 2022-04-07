package by.makei.shop.model.dao;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.entity.User;

import java.util.Optional;

public interface UserDao  {
    Optional<User> selectUserByLoginAndPassword(String login, String password) throws DaoException;

}
