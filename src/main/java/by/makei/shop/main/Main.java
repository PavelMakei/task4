package by.makei.shop.main;

import java.util.ResourceBundle;

public class Main {
    private static ResourceBundle bundle;
    public static final String BUNDLE_NAME = "test";

    public static void main(String[] args) {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME);
        System.out.println(bundle.getString("TEST1"));
        System.out.println(bundle.getString("TEST2"));
        System.out.println(bundle.getString("TEST3"));
    }
}
