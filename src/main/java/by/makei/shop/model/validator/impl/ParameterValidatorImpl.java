package by.makei.shop.model.validator.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.dao.BaseDao;
import by.makei.shop.model.dao.impl.BrandDaoImpl;
import by.makei.shop.model.dao.impl.ProductDaoImpl;
import by.makei.shop.model.dao.impl.ProductTypeDaoImpl;
import by.makei.shop.model.dao.impl.UserDaoImpl;
import by.makei.shop.model.entity.Brand;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.ProductType;
import by.makei.shop.model.entity.User;
import by.makei.shop.model.validator.AttributeValidator;
import by.makei.shop.model.validator.ParameterValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.validator.DefaultSearchParam.*;

public class ParameterValidatorImpl implements ParameterValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final ParameterValidatorImpl instance = new ParameterValidatorImpl();
    private static final int MAX_FILE_VALUE = 4194304;


    private ParameterValidatorImpl() {
    }

    public static ParameterValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateUserData(Map<String, String> userData) throws ServiceException {
        Map<String, String> invalidParameters = new HashMap<>();
        AttributeValidator validator = AttributeValidatorImpl.getInstance();
        UserDaoImpl userDao = new UserDaoImpl();
        boolean isCorrect = true;
        try {

            for (Map.Entry<String, String> entry : userData.entrySet()) {
                String key = entry.getKey();
                switch (key) {
                    case FIRST_NAME -> {
                        if (!validator.isNameValid(entry.getValue())) {
                            invalidParameters.put(INVALID_FIRST_NAME, INVALID_FIRST_NAME);
                            isCorrect = false;
                        }
                    }
                    case LAST_NAME -> {
                        if (!validator.isNameValid(entry.getValue())) {
                            invalidParameters.put(INVALID_LAST_NAME, INVALID_LAST_NAME);
                            isCorrect = false;
                        }
                    }
                    case LOGIN -> {
                        if (!validator.isLoginValid(entry.getValue())) {
                            invalidParameters.put(INVALID_LOGIN, INVALID_LOGIN);
                            isCorrect = false;
                        } else {
                            Optional<User> optionalUser = userDao.findEntityByOneParam(LOGIN, entry.getValue());
                            if (optionalUser.isPresent()) {
                                invalidParameters.put(BUSY_LOGIN, BUSY_LOGIN);
                                isCorrect = false;
                            }
                        }
                    }
                    case EMAIL -> {
                        if (!validator.isEmailValid(entry.getValue())) {
                            invalidParameters.put(INVALID_EMAIL, INVALID_EMAIL);
                            isCorrect = false;
                        } else {
                            Optional<User> optionalUser = userDao.findEntityByOneParam(EMAIL, entry.getValue());
                            if (optionalUser.isPresent()) {
                                invalidParameters.put(BUSY_EMAIL, BUSY_EMAIL);
                                isCorrect = false;
                            }
                        }
                    }
                    case PHONE -> {
                        if (!validator.isPhoneValid(entry.getValue())) {
                            invalidParameters.put(INVALID_PHONE, INVALID_PHONE);
                            isCorrect = false;
                        } else {
                            Optional<User> optionalUser = userDao.findEntityByOneParam(PHONE, entry.getValue());
                            if (optionalUser.isPresent()) {
                                invalidParameters.put(BUSY_PHONE, BUSY_PHONE);
                                isCorrect = false;
                            }
                        }
                    }
                    case PASSWORD -> {
                        if (!validator.isPasswordValid(entry.getValue())) {
                            invalidParameters.put(INVALID_PASSWORD, INVALID_PASSWORD);
                            isCorrect = false;
                        }
                    }
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
            //TODO переделать метод с передачей проверки наличия в базу в отдельные сервисы? Бросать отдельный (validator?)эесепшен?
        }
        userData.putAll(invalidParameters);
        return isCorrect;
    }

    @Override
    public boolean validateJpg(byte[] photo, int... widthAndHeight) {
        InputStream targetStream = new ByteArrayInputStream(photo);
        if (photo.length > MAX_FILE_VALUE) {
            logger.log(Level.INFO, "ParameterValidator validateJpg file too big, allowed length is 4194304, but {}", photo.length);
            return false;
        }
        boolean canRead = false;
        try (ImageInputStream iis = ImageIO.createImageInputStream(targetStream)) {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
            while (readers.hasNext()) {
                ImageReader reader = readers.next();
                reader.setInput(iis);
                reader.read(0);
                canRead = true;
                targetStream.close();
                logger.log(Level.INFO, "photo file.jpg validated true");
                break;
            }
            if (widthAndHeight.length == 0) {
                return canRead;
            }
            BufferedImage bis = ImageIO.read(new ByteArrayInputStream(photo));
            int w = bis.getWidth();
            int h = bis.getHeight();
            if (w != widthAndHeight[0] || h != widthAndHeight[1]) {
                canRead = false;
            }

        } catch (IOException exp) {
            logger.log(Level.INFO, "photo file.jpg can't be recognised");
            //it's normal work of validator
        }
        return canRead;
    }

    @Override
    public boolean validateAndCorrectSearchProductParam(Map<String, String> searchProductParam) {
        boolean isCorrect = true;
        AttributeValidator validator = AttributeValidatorImpl.getInstance();

        for (Map.Entry<String, String> entry : searchProductParam.entrySet()) {
            switch (entry.getKey()) {
                case SEARCH_BRAND_ID -> {
                    String brandId = entry.getValue();
                    if (!validator.isIntValid(brandId)) {
                        entry.setValue(DEFAULT_BRAND_ID);
                        isCorrect= false;
                    }
                }
                case SEARCH_TYPE_ID -> {
                    String typeId = entry.getValue();
                    if (!validator.isIntValid(typeId)) {
                        entry.setValue(DEFAULT_TYPE_ID);
                        isCorrect= false;
                    }
                }
                case SEARCH_MIN_PRICE -> {
                    String minPrice = entry.getValue();
                    if (!validator.isIntValid(minPrice)) {
                        entry.setValue(DEFAULT_MIN_PRICE);
                        isCorrect= false;
                    }
                }
                case SEARCH_MAX_PRICE -> {
                    String maxPrice = entry.getValue();
                    if (!validator.isIntValid(maxPrice)) {
                        entry.setValue(DEFAULT_MAX_PRICE);
                        isCorrect= false;
                    }
                }
                case SEARCH_MIN_POWER -> {
                    String minPower = entry.getValue();
                    if (!validator.isIntValid(minPower)) {
                        entry.setValue(DEFAULT_MIN_POWER);
                        isCorrect= false;
                    }
                }
                case SEARCH_MAX_POWER -> {
                    String maxPower = entry.getValue();
                    if (!validator.isIntValid(maxPower)) {
                        entry.setValue(DEFAULT_MAX_POWER);
                        isCorrect= false;
                    }
                }
                case SEARCH_PAGE -> {
                    String searchPage = entry.getValue();
                    if (!validator.isIntValid(searchPage)) {
                        entry.setValue(DEFAULT_PAGE);
                        isCorrect= false;
                    }
                }
            }
        }
        return isCorrect;
    }

    @Override
    public boolean validateProductData(Map<String, String> productData, byte[] photoJpg) throws ServiceException {
        boolean isCorrect = true;
        Map<String, String> invalidParameters = new HashMap<>();
        AttributeValidator validator = AttributeValidatorImpl.getInstance();
        BaseDao<Product> productDao = new ProductDaoImpl();
        BaseDao<Brand> brandDao = new BrandDaoImpl();
        ProductTypeDaoImpl productTypeDao = new ProductTypeDaoImpl();
        try {
            for (Map.Entry<String, String> entry : productData.entrySet()) {
                String key = entry.getKey();
                switch (key) {
                    case BRAND_ID -> {
                        if (!validator.isIntValid(entry.getValue())) {
                            invalidParameters.put(INVALID_BRAND_ID, INVALID_BRAND_ID);
                            isCorrect = false;
                        } else {
                            Optional<Brand> optionalBrand = brandDao.findEntityByOneParam(ID, entry.getValue());
                            if (optionalBrand.isEmpty()) {
                                invalidParameters.put(INVALID_BRAND_ID, INVALID_BRAND_ID);
                                isCorrect = false;
                            }
                        }
                    }
                    case TYPE_ID -> {
                        if (!validator.isIntValid(entry.getValue())) {
                            invalidParameters.put(INVALID_TYPE_ID, INVALID_TYPE_ID);
                            isCorrect = false;
                        } else {
                            Optional<ProductType> optionalType = productTypeDao.findEntityByOneParam(ID, entry.getValue());
                            if (optionalType.isEmpty()) {
                                invalidParameters.put(INVALID_TYPE_ID, INVALID_TYPE_ID);
                                isCorrect = false;
                            }
                        }
                    }
                    case PRODUCT_NAME -> {
                        if (!validator.isProductNameValid(entry.getValue())) {
                            invalidParameters.put(INVALID_PRODUCT_NAME, INVALID_PRODUCT_NAME);
                            isCorrect = false;
                        } else {
                            Optional<Product> optionalProduct = productDao.findEntityByOneParam(PRODUCT_NAME, entry.getValue());
                            if (optionalProduct.isPresent()) {
                                invalidParameters.put(BUSY_PRODUCT_NAME, BUSY_PRODUCT_NAME);
                                isCorrect = false;
                            }
                        }
                    }
                    case DESCRIPTION -> {
                        if (!validator.isDescriptionValid(entry.getValue())) {
                            invalidParameters.put(INVALID_DESCRIPTION, INVALID_DESCRIPTION);
                            isCorrect = false;
                        }
                    }
                    case PRICE -> {
                        if (!validator.isDecimalValid(entry.getValue())) {
                            invalidParameters.put(INVALID_PRICE, INVALID_PRICE);
                            isCorrect = false;
                        }
                    }
                    case COLOUR -> {
                        if (!validator.isColourValid(entry.getValue())) {
                            invalidParameters.put(INVALID_COLOUR, INVALID_COLOUR);
                            isCorrect = false;
                        }
                    }
                    case POWER -> {
                        if (!validator.isIntValid(entry.getValue())) {
                            invalidParameters.put(INVALID_POWER, INVALID_POWER);
                            isCorrect = false;
                        }
                    }
                    case SIZE -> {
                        if (!validator.isSizeValid(entry.getValue())) {
                            invalidParameters.put(INVALID_SIZE, INVALID_SIZE);
                            isCorrect = false;
                        }
                    }
                    case QUANTITY_IN_STOCK -> {
                        if (!validator.isIntValid(entry.getValue())) {
                            invalidParameters.put(INVALID_QUANTITY_IN_STOCK, INVALID_QUANTITY_IN_STOCK);
                            isCorrect = false;
                        }
                    }
                }
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ParameterValidator error while validateProductData {}", e.getMessage());
            throw new ServiceException("ParameterValidator error while validateProductData", e);
            //TODO Создать новый validator exception?
        }

        if (!validateJpg(photoJpg)) {
            invalidParameters.put(INVALID_PHOTO, INVALID_PHOTO);
            isCorrect = false;
        }

        productData.putAll(invalidParameters);
        return isCorrect;
    }
}
