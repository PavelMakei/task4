package by.makei.shop.model.service;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.User;

import java.util.Optional;

public interface UserService {
    //TODO
    Optional<User> signIn(String login, String password) throws ServiceException;
}
