package by.makei.shop.model.validator;

public interface AttributeValidator {

    boolean isNameValid(String name);

    boolean isLoginValid(String login);

    boolean isPasswordValid(String password);

    boolean isEmailValid(String email);

    boolean isPhoneValid(String phone);

    boolean isDecimalValid(String decimalStr);

    //TODO add methods??
}
