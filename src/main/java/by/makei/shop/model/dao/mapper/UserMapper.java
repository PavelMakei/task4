package by.makei.shop.model.dao.mapper;

import by.makei.shop.model.entity.AccessLevel;
import by.makei.shop.model.entity.User;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserMapper implements Mapper{
    private static final String USER_ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String ACCESS_LEVEL = "access_level";
    private static final String REGISTRATION_DATE = "registration_date";
    private static final String MONEY_AMOUNT = "money_amount";

    public Optional<User> mapEntity(ResultSet resultSet){
        User user = new User();
        Optional<User> optionalUser;

        try {
            user.setId(resultSet.getInt(USER_ID));
            user.setFirstName(resultSet.getString(FIRST_NAME));
            user.setLastName(resultSet.getString(LAST_NAME));
            user.setLogin(resultSet.getString(LOGIN));
            user.setEmail(resultSet.getString(EMAIL));
            user.setPhone(resultSet.getString(PHONE));
            user.setAccessLevel(AccessLevel.valueOf(resultSet.getString(ACCESS_LEVEL)));
            user.setDate(resultSet.getDate(REGISTRATION_DATE));
            user.setAmount(BigDecimal.valueOf(resultSet.getDouble(MONEY_AMOUNT)));

            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            //TODO
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }






}
