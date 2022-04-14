package by.makei.shop.model.dao.mapper;

import by.makei.shop.model.entity.AbstractEntity;

import java.sql.ResultSet;
import java.util.Optional;

public interface Mapper <T extends AbstractEntity> {
    Optional<T> mapEntity (ResultSet resultSet);

}
