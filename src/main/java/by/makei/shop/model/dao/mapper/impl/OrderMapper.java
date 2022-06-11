package by.makei.shop.model.dao.mapper.impl;

import by.makei.shop.model.dao.mapper.Mapper;
import by.makei.shop.model.entity.Order;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.*;

public class OrderMapper implements Mapper<Order> {

    @Override
    public Optional<Order> mapEntity(@NotNull ResultSet resultSet) {
        Optional<Order> optionalOrder;
        Order order = new Order();
        try{
            order.setUserId(resultSet.getInt(USER_ID));
            order.setAddress(resultSet.getString(ADDRESS));
            order.setPhone(resultSet.getString(PHONE));
            order.setDetails(resultSet.getString(DETAIL));

        } catch (SQLException e) {
          //TODO!!!!!!!!!!
        }
        return null; //TODO!!!!!!!!!!!!!!
    }
}
