package by.makei.shop.model.validator.impl;

import by.makei.shop.model.validator.AttributeValidator;
import by.makei.shop.model.validator.ValidatorPattern;

import static by.makei.shop.model.command.AttributeName.*;

public class AttributeValidatorImpl implements AttributeValidator {
    private static final AttributeValidatorImpl instance = new AttributeValidatorImpl();
    private static final ValidatorPattern validatorPattern = ValidatorPattern.getInstance();
//    public static final String NAME_PATTERN = "^[A-Za-zА-ЯЁа-яё]{3,20}$";
//    public static final String LOGIN_PATTERN = "^[A-Za-zА-ЯЁа-яё\\d_]{4,16}$";
//    public static final String PASSWORD_PATTERN = "^[A-Za-zА-ЯЁа-яё\\d_!@#,\\.]{6,16}$";
//    public static final String EMAIL_PATTERN = "^[^[\\d\\.]][A-Za-z\\.\\d]{1,30}@[a-z]{2,10}\\.([a-z]{2,4}|[a-z]{2,4}\\.[a-z]{2,4})$";
//    public static final String PHONE_PATTERN = "^\\((025|029|044)\\)\\d{7}$";
//    public static final String DECIMAL_STRING_PATTERN = "^((\\d{1,5}\\.\\d{0,2})|(\\d{1,5}))$";
//    public static final String INTEGER_STRING_PATTERN = "^((\\d{1,5}))$";
//    public static final String PRODUCT_NAME_PATTERN = "^[A-Za-zА-ЯЁа-яё\\d_,\\.,;:\\- ]{3,60}$";
//    public static final String DESCRIPTION_PATTERN = "^[A-Za-zА-ЯЁа-яё\\d_ -\\.;,\\(\\)]+$";
//    public static final String COLOUR_PATTERN = "^[A-Za-zА-ЯЁа-яё\\d\\-_ ]{3,60}$";
//    public static final String SIZE_PATTERN = "^[A-Za-zА-ЯЁа-яё\\d_* ]{3,45}$";
//    public static final String ZERO_ONE_PATTERN = "^[01]$";


    private AttributeValidatorImpl(){}

    public static AttributeValidator getInstance (){
        return instance;
    }

    @Override
    public boolean isNameValid(String name) {
        return (name != null && name.matches(validatorPattern.getNamePattern()));
    }

    @Override
    public boolean isLoginValid(String login) {
        return (login != null && login.matches(validatorPattern.getLoginPattern()));
    }

    @Override
    public boolean isPasswordValid(String password) {
        return (password != null && password.matches(validatorPattern.getPasswordPattern()));
    }

    @Override
    public boolean isEmailValid(String email) {
        return (email != null && email.matches(validatorPattern.getEmailPattern()));
    }

    @Override
    public boolean isPhoneValid(String phone) {
        return (phone != null && phone.matches(validatorPattern.getPhonePattern()));
    }

    @Override
    public boolean isDecimalValid(String decimalStr) {
        return (decimalStr != null && decimalStr.matches(validatorPattern.getDecimalStringPattern()));
    }

    @Override
    public boolean isInt3Valid(String idValue) {
        return (idValue != null && idValue.matches(validatorPattern.getInteger3StringPattern()));
    }
    @Override
    public boolean isInt5Valid(String idValue) {
        return (idValue != null && idValue.matches(validatorPattern.getInteger5StringPattern()));
    }

    @Override
    public boolean isProductNameValid(String productName) {
        return (productName != null && productName.matches(validatorPattern.getProductNamePattern()));
    }

    @Override
    public boolean isDescriptionValid(String description) {
        return (description != null && description.matches(validatorPattern.getDescriptionPattern()));
    }

    @Override
    public boolean isColourValid(String colour) {
        return (colour != null && colour.matches(validatorPattern.getColourPattern()));
    }

    @Override
    public boolean isSizeValid(String size) {
        return (size != null && size.matches(validatorPattern.getSizePattern()));
    }

    @Override
    public boolean isPageButtonValid(String pageButton) {
        return (pageButton != null && (pageButton.matches(NEXT_PAGE)||pageButton.matches(PREVIOUS_PAGE)||pageButton.matches(SEARCH_PAGE)));
    }

    @Override
    public boolean isZeroOneValid(String zeroOne) {
        return (zeroOne != null && zeroOne.matches(validatorPattern.getZeroOnePattern()));
    }

    @Override
    public boolean isCardNumberValid(String cardNumber) {
        return (cardNumber != null && cardNumber.matches(validatorPattern.getCardNumberPattern()));
    }

    @Override
    public boolean isCardExpDateValid(String cardExpDate) {
        return (cardExpDate!= null && cardExpDate.matches(validatorPattern.getCardExpDatePattern()));
    }

    @Override
    public boolean isCardHolderValid(String cardHolder) {
        return (cardHolder != null && cardHolder.matches(validatorPattern.getCardHolderPattern()));
    }


}
