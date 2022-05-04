package by.makei.shop.model.service.mail;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MailServiceTest {
public static final String SEND_EMAIL_TO = "forjavatest.mail@gmail.com";
public static final String MAIL_SUBJECT= "mail subject";
public static final String MAIL_TEXT= "This is text message. \n It is just for test.";

    @Test
    void sendEmailTest() {
        MailService mailService = new MailService();
        boolean actual = mailService.sendEmail(SEND_EMAIL_TO,MAIL_SUBJECT,MAIL_TEXT);
        assertTrue(actual);
    }
}