package by.makei.shop.model.validator;

public interface AttributeValidator {

    boolean isNameValid(String name);

    boolean isLoginValid(String login);

    boolean isPasswordValid(String password);

    boolean isEmailValid(String email);

    boolean isPhoneValid(String phone);

    boolean isDecimalValid(String decimalStr);

    boolean isInt3Valid(String idValue);

    boolean isInt5Valid(String idValue);

    boolean isProductNameValid(String productName);

    boolean isDescriptionValid(String description);


    boolean isColourValid(String colour);

    boolean isSizeValid(String size);

    boolean isPageButtonValid(String pageButton);

    boolean isZeroOneValid(String zeroOne);

    boolean isCardNumberValid(String cardNumber);

    boolean isCardExpDateValid(String cardExpDate);

    boolean isCardHolderValid(String cardHolder);

    boolean isDetailValid(String detail);


}
