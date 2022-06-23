package by.makei.shop.model.dao.mapper.impl;

import by.makei.shop.model.dao.mapper.Mapper;
import by.makei.shop.model.entity.ProductType;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.makei.shop.command.AttributeName.ID;
import static by.makei.shop.command.AttributeName.TYPE_NAME;

public class TypeMapper implements Mapper<ProductType> {

    @Override
    public Optional<ProductType> mapEntity(ResultSet resultSet) {
        ProductType productType = new ProductType();
        Optional<ProductType> optionalProductType = Optional.empty();
        try {
            productType.setId(resultSet.getInt(ID));
            productType.setTypeName(resultSet.getString(TYPE_NAME));
            optionalProductType = Optional.of(productType);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException brand wasn't mapped in BrandMapper. {}", e.getMessage());
            optionalProductType = Optional.empty();
        }
        return optionalProductType;
    }
}
