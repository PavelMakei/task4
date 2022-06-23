package by.makei.shop.model.dao.mapper.impl;

import by.makei.shop.model.dao.mapper.Mapper;
import by.makei.shop.model.entity.AccessLevel;
import by.makei.shop.model.entity.User;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.makei.shop.command.AttributeName.*;

public class UserMapper implements Mapper<User> {

    public Optional<User> mapEntity(ResultSet resultSet) {
        User user = new User();
        Optional<User> optionalUser;
        try {
            user.setId(resultSet.getInt(ID));
            user.setFirstName(resultSet.getString(FIRST_NAME));
            user.setLastName(resultSet.getString(LAST_NAME));
            user.setLogin(resultSet.getString(LOGIN.toLowerCase()));
            user.setEmail(resultSet.getString(EMAIL.toLowerCase()));
            user.setPhone(resultSet.getString(PHONE));
            user.setAccessLevel(AccessLevel.valueOf(resultSet.getString(ACCESS_LEVEL)));
            user.setDate(resultSet.getTimestamp(REGISTRATION_DATE).toInstant());
            user.setAmount(BigDecimal.valueOf(resultSet.getDouble(MONEY_AMOUNT)));
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            logger.log(Level.ERROR,"SQLException user wasn't mapped in UserMapper. {}", e.getMessage());
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }


}
