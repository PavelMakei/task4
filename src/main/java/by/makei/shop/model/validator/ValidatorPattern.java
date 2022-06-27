package by.makei.shop.model.validator;

/**
 * contains set of regex patterns and getters for them
 */
public class ValidatorPattern {
    private static final ValidatorPattern instance = new ValidatorPattern();
    private ValidatorPattern(){}

    private final String loginPattern = "^[A-Za-zА-ЯЁа-яё\\d_]{4,16}$";
    private final String namePattern = "^[A-Za-zА-ЯЁа-яё]{3,20}$";
    private final String passwordPattern = "^[A-Za-zА-ЯЁа-яё\\d_!@#,\\.]{6,16}$";
    private final String emailPattern = "^(?![_.-])((?![_.-][_.-])[a-zA-Z\\d_.-]){0,63}[a-zA-Z\\d]@((?!-)((?!--)[a-zA-Z\\d-]){0,63}[a-zA-Z\\d]\\.){1,2}([a-zA-Z]{2,14}\\.)?[a-zA-Z]{2,14}$";
    private final String phonePattern = "^\\((025|029|044)\\)\\d{7}$";
    private final String decimalStringPattern = "^((\\d{1,5}\\.\\d{0,2})|(\\d{1,5}))$";
    private final String integer5StringPattern = "^((\\d{1,5}))$";
    private final String integer3StringPattern = "^((\\d{1,3}))$";
    private final String productNamePattern = "^[A-Za-zА-ЯЁа-яё\\d_,\\.,;:\\- ]{3,60}$";
    private final String descriptionPattern = "^[A-Za-zА-ЯЁа-яё\\d_ \\s-\\.;,\\(\\)]+$";
    private final String colourPattern = "^[A-Za-zА-ЯЁа-яё\\d\\-_ ]{3,60}$";
    private final String sizePattern = "^[A-Za-zА-ЯЁа-яё\\d_* ]{3,45}$";
    private final String zeroOnePattern = "^[01]$";
    private final String quantityToByPattern = "^([1-9]|(10))$";
    private final String cardNumberPattern = "^([\\d]{16})$";
    private final String cardExpDatePattern = "^(1[0-2]|0[1-9])\\/(20[0-9]{2})$";
    private final String cardHolderPattern = "^([A-Z]+ [A-Z]+)$";


    public static ValidatorPattern getInstance() {
        return instance;
    }

    public String getLoginPattern() {
        return loginPattern;
    }

    public String getNamePattern() {
        return namePattern;
    }

    public String getPasswordPattern() {
        return passwordPattern;
    }

    public String getEmailPattern() {
        return emailPattern;
    }

    public String getPhonePattern() {
        return phonePattern;
    }

    public String getDecimalStringPattern() {
        return decimalStringPattern;
    }

    public String getInteger5StringPattern() {
        return integer5StringPattern;
    }

    public String getProductNamePattern() {
        return productNamePattern;
    }

    public String getDescriptionPattern() {
        return descriptionPattern;
    }

    public String getColourPattern() {
        return colourPattern;
    }

    public String getSizePattern() {
        return sizePattern;
    }

    public String getZeroOnePattern() {
        return zeroOnePattern;
    }

    public String getInteger3StringPattern() { return integer3StringPattern; }

    public String getQuantityToByPattern() {
        return quantityToByPattern;
    }

    public String getCardNumberPattern() {
        return cardNumberPattern;
    }

    public String getCardExpDatePattern() {
        return cardExpDatePattern;
    }

    public String getCardHolderPattern() {
        return cardHolderPattern;
    }
}
