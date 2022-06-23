package by.makei.shop.model.service.mail;

import by.makei.shop.util.ResourceManager;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import static by.makei.shop.command.AttributeName.EMAIL;
import static by.makei.shop.command.AttributeName.LOCALE;

public final class MailSender {
    private static final String LOCALE_SPLIT_REGEXP = "_";
    private static final String MESSAGE_SHOP_ACTIVATION_CODE = "lightingshop.activation.code";
    private static final String MESSAGE_YOUR_ACTIVATION_CODE = "your.activation.code.is";

    private MailSender(){}


    public static void sendActivationCodeByEmail(HttpServletRequest request, String activationCode) {
        ResourceManager manager = ResourceManager.INSTANCE;
        String locale = request.getSession().getAttribute(LOCALE).toString();
        String[] languageAndCountry = locale.split(LOCALE_SPLIT_REGEXP);
        manager.changeResource(new Locale(languageAndCountry[0],languageAndCountry[1]));
        String sendEmailTo = request.getParameter(EMAIL);
        StringBuilder mailText = new StringBuilder("");
        mailText.append(manager.getString(MESSAGE_SHOP_ACTIVATION_CODE))
                .append("\n")
                .append(manager.getString(MESSAGE_YOUR_ACTIVATION_CODE))
                .append(" :")
                .append(activationCode);
        String mailSubject = manager.getString(MESSAGE_SHOP_ACTIVATION_CODE);
        MailService mailService = new MailService();
        mailService.sendEmail(sendEmailTo,mailSubject,mailText.toString());
    }

}
