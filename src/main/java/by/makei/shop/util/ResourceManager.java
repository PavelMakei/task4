package by.makei.shop.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ResourceManager {
    //TODO переписать на какой-нить другой синглтон
    INSTANCE;
    private ResourceBundle resourceBundle;
    private final String resourceName = "language_text";

    private ResourceManager() {
        resourceBundle = ResourceBundle.getBundle(resourceName, Locale.getDefault());
    }

    public void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(resourceName, locale);
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}