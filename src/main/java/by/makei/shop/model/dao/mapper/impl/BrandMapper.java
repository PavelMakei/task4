package by.makei.shop.model.dao.mapper.impl;

import by.makei.shop.model.dao.mapper.Mapper;
import by.makei.shop.model.entity.Brand;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.makei.shop.command.AttributeName.BRAND_NAME;
import static by.makei.shop.command.AttributeName.ID;


public class BrandMapper implements Mapper<Brand> {

    public Optional<Brand> mapEntity(ResultSet resultSet) {
        Brand brand = new Brand();
        Optional<Brand> optionalBrand = Optional.empty();
        try {
            brand.setId(resultSet.getInt(ID));
            brand.setBrandName(resultSet.getString(BRAND_NAME));
            optionalBrand = Optional.of(brand);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException brand wasn't mapped in BrandMapper. {}", e.getMessage());
            optionalBrand = Optional.empty();
        }
        return optionalBrand;
    }
}
