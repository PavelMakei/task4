package by.makei.shop.model.validator.impl;

import by.makei.shop.model.validator.AttributeValidator;
import by.makei.shop.model.validator.ValidatorPattern;

import static by.makei.shop.command.AttributeName.*;

public class AttributeValidatorImpl implements AttributeValidator {
    private static final AttributeValidatorImpl instance = new AttributeValidatorImpl();
    private static final ValidatorPattern validatorPattern = ValidatorPattern.getInstance();
    private static final String EMPTY_STRING = "";


    private AttributeValidatorImpl() {
    }

    public static AttributeValidator getInstance() {
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
        return (pageButton != null && (pageButton.matches(NEXT_PAGE) || pageButton.matches(PREVIOUS_PAGE) || pageButton.matches(SEARCH_PAGE)));
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
        return (cardExpDate != null && cardExpDate.matches(validatorPattern.getCardExpDatePattern()));
    }

    @Override
    public boolean isCardHolderValid(String cardHolder) {
        return (cardHolder != null && cardHolder.matches(validatorPattern.getCardHolderPattern()));
    }

    @Override
    public boolean isDetailValid(String detail) {
        return (detail == null || detail.equals(EMPTY_STRING) || detail.matches(validatorPattern.getDescriptionPattern()));
    }


}
