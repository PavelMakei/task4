package by.makei.shop.model.validator.impl;

import by.makei.shop.model.validator.AttributeValidator;

public class AttributeValidatorImpl implements AttributeValidator {
    private static final AttributeValidatorImpl instance = new AttributeValidatorImpl();
    public static final String NAME_PATTERN = "^[A-Za-zА-Яа-я]{3,20}$";
    public static final String LOGIN_PATTERN = "^[A-Za-zА-Яа-я0-9_]{4,16}$";
    public static final String PASSWORD_PATTERN = "^[A-Za-zА-Яа-я0-9_!@#,\\.]{6,16}$";
    public static final String EMAIL_PATTERN = "^[^[\\d\\.]][A-Za-z\\.\\d]{1,30}@[a-z]{2,10}\\.([a-z]{2,4}|[a-z]{2,4}\\.[a-z]{2,4})$";
    public static final String PHONE_PATTERN = "^\\((025|029|044)\\)\\d{7}$";
    public static final String DECIMAL_STRING_PATTERN = "^((\\d{1,5}\\.\\d{0,2})|(\\d{1,5}))$";
    public static final String INTEGER_STRING_PATTERN = "^((\\d{1,5}))$";
    public static final String PRODUCT_NAME_PATTERN = "^[A-Za-zА-Яа-я0-9_\\- ]{3,60}$";
    public static final String DESCRIPTION_PATTERN = "^[A-Za-zА-Яа-я0-9_ //.;,//(//)]+$";
    public static final String COLOUR_PATTERN = "^[A-Za-zА-Яа-я0-9\\-_ ]{3,60}$";
    public static final String SIZE_PATTERN = "^[A-Za-zА-Яа-я0-9_* ]{3,45}$";



    private AttributeValidatorImpl(){}

    public static AttributeValidator getInstance (){
        return instance;
    }

    @Override
    public boolean isNameValid(String name) {
        return (name != null && name.matches(NAME_PATTERN));
    }

    @Override
    public boolean isLoginValid(String login) {
        return (login != null && login.matches(LOGIN_PATTERN));
    }

    @Override
    public boolean isPasswordValid(String password) {
        return (password != null && password.matches(PASSWORD_PATTERN));
    }

    @Override
    public boolean isEmailValid(String email) {
        return (email != null && email.matches(EMAIL_PATTERN));
    }

    @Override
    public boolean isPhoneValid(String phone) {
        return (phone != null && phone.matches(PHONE_PATTERN));
    }

    @Override
    public boolean isDecimalValid(String decimalStr) {
        return (decimalStr != null && decimalStr.matches(DECIMAL_STRING_PATTERN));
    }

    @Override
    public boolean isIntValid(String idValue) {
        return (idValue != null && idValue.matches(INTEGER_STRING_PATTERN));
    }

    @Override
    public boolean isProductNameValid(String productName) {
        return (productName != null && productName.matches(PRODUCT_NAME_PATTERN));
    }

    @Override
    public boolean isDescriptionValid(String description) {
        return (description != null && description.matches(DESCRIPTION_PATTERN));
    }

    @Override
    public boolean isColourValid(String colour) {
        return (colour != null && colour.matches(COLOUR_PATTERN));
    }

    @Override
    public boolean isSizeValid(String size) {
        return (size != null && size.matches(SIZE_PATTERN));
    }


}
