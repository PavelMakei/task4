package by.makei.shop.main;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class Main {
    private static ResourceBundle bundle;
    public static final String BUNDLE_NAME = "test";
    public static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME);
        logger.log(Level.INFO,bundle.getString("TEST1"));
        logger.log(Level.INFO,bundle.getString("TEST2"));
        logger.log(Level.INFO,bundle.getString("TEST3"));

    }
}
