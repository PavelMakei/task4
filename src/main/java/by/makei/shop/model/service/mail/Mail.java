package by.makei.shop.model.service.mail;


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import jakarta.mail.Session;
import java.util.Properties;

class Mail { // limited visible
    private static final Logger logger = LogManager.getLogger();
    private static final String MAILSESSION_DEBUG_PROPERTY_NAME = "mailSession.debug";
    private final String sendToEmail;
    private final String mailSubject;
    private final String mailText;
    private final Properties properties;
    private MimeMessage message;


    Mail(String sendToEmail,
         String mailSubject, String mailText, Properties properties) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.properties = properties;
    }

    private boolean init() {

        Session mailSession = SessionCreator.createSession(properties);
        boolean isMailSessionDebugOn = Boolean.parseBoolean(properties.getProperty(MAILSESSION_DEBUG_PROPERTY_NAME));
        mailSession.setDebug(isMailSessionDebugOn);
        message = new MimeMessage(mailSession);
        try {
            message.setSubject(mailSubject);
            message.setContent(mailText, "text/plain; charset=UTF-8");
//            message.setText(mailText, "UTF-8");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
            return true;
        } catch (AddressException e) {
            logger.log(Level.ERROR, "Mail incorrect email: {}, {}", sendToEmail, e.getMessage());
            return false;
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Mail error while message is creating. {}", e.getMessage());
            return false;
        }
    }

    public boolean sendEmail() {
        if(!init()){
            return false;
        }
        try {
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Mail error while message is sending. {}", e.getMessage());
            return false;
        }
    }
}

