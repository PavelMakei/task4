package by.makei.shop.model.validator;

import by.makei.shop.exception.ServiceException;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public interface ParameterValidator {

    boolean validateAndMarkUserData(Map<String, String> userData) throws ServiceException;

    boolean validateAndMarkProductData(Map<String, String> productData) throws ServiceException;

    boolean validateAndMarkIfPhoneCorrectAndNotExistsInDb(Map<String, String> userData) throws ServiceException;

    boolean validateAndMarkIfEmailCorrectAndNotExistsInDb(Map<String, String> userData) throws ServiceException;

    boolean validateAndMarkIfLoginCorrectAndNotExistsInDb(Map<String, String> userData) throws ServiceException;

    boolean validateAndMarkActivationCodeAndSavedEmail(Map<String, String> userData, HttpSession session);

    boolean validateJpg(byte[] photo, int... widthAndHeight);

    boolean validateAndCorrectSearchProductParam(Map<String,String> searchProductData, Map<String,String> orderByParamQuery);

    boolean ifProductNameCorrectAndNotExistsInDb(Map<String, String> productData) throws ServiceException;

    boolean validatePhoto(Map<String, String> productData, byte[] photoJpg) throws ServiceException;

    boolean validateAndMarkDepositData(Map<String, String> depositDataMap);
}
