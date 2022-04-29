package by.makei.shop.model.dao.mapper;

import by.makei.shop.model.entity.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.Optional;

public interface Mapper <T extends AbstractEntity> {
    Logger logger = LogManager.getLogger();

    Optional<T> mapEntity (ResultSet resultSet);

}
