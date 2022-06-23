package by.makei.shop.model.dao.mapper.impl;

import by.makei.shop.model.dao.mapper.Mapper;
import by.makei.shop.model.entity.Order;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.makei.shop.command.AttributeName.*;

public class OrderMapper implements Mapper<Order> {

    @Override
    public Optional<Order> mapEntity(@NotNull ResultSet resultSet) {
        Optional<Order> optionalOrder;
        Order order = new Order();
        try {
            order.setId(resultSet.getInt(ID));
            order.setUserId(resultSet.getInt(USER_ID));
            order.setAddress(resultSet.getString(ADDRESS));
            order.setPhone(resultSet.getString(PHONE));
            order.setDetail(resultSet.getString(DETAIL));
            order.setStatus(Order.Status.valueOf(resultSet.getString(STATUS)));
            order.setOpenDate(resultSet.getDate(DATE_OPEN));
            order.setCloseDate(resultSet.getDate(DATE_CLOSE));
            optionalOrder = Optional.of(order);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException order wasn't mapped in OrderMapper. {}", e.getMessage());
            optionalOrder = Optional.empty();
        }
        return optionalOrder;
    }
}
