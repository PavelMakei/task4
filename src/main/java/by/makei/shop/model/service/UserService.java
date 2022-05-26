package by.makei.shop.model.service;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.entity.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    //TODO
    Optional<User> signIn(String login, String password) throws ServiceException;
    boolean createUser(Map<String, String> userData) throws ServiceException;

    List<User> findAllUser() throws ServiceException;

    boolean updateAccessLevel(Map<String, String> userDataMap) throws ServiceException;

    Optional<User>findUserByEmail(String email) throws ServiceException;

    boolean updatePassword(Map<String, String> userDataMap) throws ServiceException;

    boolean updateProfile(Map<String, String> userDataMap) throws ServiceException;

    Optional<User> findUserByOneParam(String paramName, String paramValue) throws ServiceException;
}
