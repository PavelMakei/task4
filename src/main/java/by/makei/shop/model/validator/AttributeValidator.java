package by.makei.shop.model.validator;

public interface AttributeValidator {

    boolean isNameValid(String name);

    boolean isLoginValid(String login);

    boolean isPasswordValid(String password);

    boolean isEmailValid(String email);

    boolean isPhoneValid(String phone);

    boolean isDecimalValid(String decimalStr);

    boolean isIntValid(String intString);

    boolean isProductNameValid(String productName);

    boolean isDescriptionValid(String description);


    boolean isColourValid(String colour);

    boolean isSizeValid(String size);


    //TODO add methods??
}
