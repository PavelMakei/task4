package by.makei.shop.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ResourceManager {
    INSTANCE;
    private ResourceBundle resourceBundle;
    private final String resourceName = "language_text";

    private ResourceManager() {
        resourceBundle = ResourceBundle.getBundle(resourceName, Locale.getDefault());
    }

    /**
     * change current locale
     * @param locale
     */
    public void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(resourceName, locale);
    }

    /**
     * Translate with "language_text" resources
     * @param key
     * @return translated to current locale key
     */
    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}