package by.makei.shop.model.service.mail;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;



import java.util.Properties;

public class SessionCreator {

    public static Session createSession(Properties sessionProperties) {
        String userName = sessionProperties.getProperty("mail.user.name");
        String userPassword = sessionProperties.getProperty("mail.user.password");
        return Session.getDefaultInstance(sessionProperties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, userPassword);
                    }
                });
    }



}
