package by.makei.shop.model.dao.mapper.impl;

import by.makei.shop.model.dao.mapper.Mapper;
import by.makei.shop.model.entity.Product;
import by.makei.shop.util.ImageConverter;
import org.apache.logging.log4j.Level;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.makei.shop.command.AttributeName.*;

public class ProductMapper implements Mapper<Product> {

    @Override
    public Optional<Product> mapEntity(ResultSet resultSet) {
        Product product = new Product();
        Optional<Product> optionalProduct = Optional.empty();
        try {
            product.setId(resultSet.getInt(ID));
            product.setBrandId(resultSet.getInt(BRAND_ID));
            product.setTypeId(resultSet.getInt(TYPE_ID));
            product.setProductName(resultSet.getString(PRODUCT_NAME));
            product.setDescription(resultSet.getString(DESCRIPTION));
            product.setPrice(resultSet.getDouble(PRICE));
            product.setColour(resultSet.getString(COLOUR));
            product.setPower(resultSet.getInt(POWER));
            product.setSize(resultSet.getString(SIZE));

            Blob blobPhoto = resultSet.getBlob(PHOTO);
            if (blobPhoto != null) {
                byte[] imageContent = blobPhoto.getBytes(1, (int) blobPhoto.length());
                String encodeBase64 = ImageConverter.imageToString(imageContent);
                //TODO scale image???
                product.setPhotoString(encodeBase64);
            }

            optionalProduct = Optional.of(product);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException product wasn't mapped in ProductMapper. {}", e.getMessage());
            optionalProduct = Optional.empty();
        }
        return optionalProduct;
    }




}
