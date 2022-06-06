package by.makei.shop.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Map;

import static by.makei.shop.model.command.AttributeName.LOCALE;

public class MessageReinstall {
//    private static final String LOCALE_SPLIT_REGEXP = "_";
    private MessageReinstall(){}

    public static boolean extractAndSetMessage(String messageAttrParam,HttpServletRequest request){
//        ResourceManager manager = ResourceManager.INSTANCE;
//        String locale = request.getSession().getAttribute(LOCALE).toString();
//        String[] languageAndCountry = locale.split(LOCALE_SPLIT_REGEXP);
//        manager.changeResource(new Locale(languageAndCountry[0],languageAndCountry[1]));
//        manager.getString(parameters.get(messageAttrParam)[0]);

        boolean isExists = false;
        Map<String,String[]> parameters = request.getParameterMap();
        if(parameters.containsKey(messageAttrParam)){
            request.setAttribute(messageAttrParam,parameters.get(messageAttrParam)[0]);
            isExists = true;
        }
        return isExists;
    }
}
