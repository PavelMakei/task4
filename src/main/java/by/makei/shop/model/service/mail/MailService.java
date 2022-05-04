package by.makei.shop.model.service.mail;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class MailService {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONFIG_FILE_NAME = "mail/mail.properties";
    private String propertiesFile;
    private Properties properties = new Properties();



    public boolean sendEmail(String sendEmailTo, String mailSubject, String mailText) {
        URL resource = getUrl();
        if(resource == null){
            logger.log(Level.ERROR, "MailService getUrl file - {} - not found!", CONFIG_FILE_NAME);
            return false;
        }
        propertiesFile = resource.getFile();
        if(!loadPropertyFromFile()){
            return false;
        }
        Mail mail = new Mail(sendEmailTo, mailSubject, mailText, properties);
        return (mail.sendEmail());
    }

    private boolean loadPropertyFromFile() {

        try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
            properties.load(fileInputStream);
            return true;
        } catch (IOException e) {
            logger.log(Level.FATAL, "MailService loadPropertyFromFile config property file - {} - can't be read", CONFIG_FILE_NAME);
            return false;
//            throw new ServiceException("MailService loadPropertyFromFile config property file can't be read", e);
        }
    }

    private URL getUrl() {
        ClassLoader classLoader = SessionCreator.class.getClassLoader();
        return classLoader.getResource(CONFIG_FILE_NAME);
    }
}
