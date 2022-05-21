package by.makei.shop.model.validator;

import by.makei.shop.exception.ServiceException;

import java.util.Map;

public interface ParameterValidator {

    boolean validateUserData(Map<String, String> userData) throws ServiceException;

    boolean validateProductData(Map<String, String> productData) throws ServiceException;

    boolean validateJpg(byte[] photo, int... widthAndHeight);

    boolean validateAndCorrectSearchProductParam(Map<String,String> searchProductData, Map<String,String> orderByParamQuery);

    boolean validatePhoto(Map<String, String> productData, byte[] photoJpg) throws ServiceException;
}
