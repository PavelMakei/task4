package by.makei.shop.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class MessageReinstall {
//    private static final String LOCALE_SPLIT_REGEXP = "_";
    private MessageReinstall(){}

    /**
     * find value of messageAttrParam in request parameters and set it into request attribute
     * @param messageAttrParam - String key
     * @param request
     * @return true if parameter was found
     */
    public static boolean extractAndSetMessage(String messageAttrParam,HttpServletRequest request){
        boolean isExists = false;
        Map<String,String[]> parameters = request.getParameterMap();
        if(parameters.containsKey(messageAttrParam)){
            request.setAttribute(messageAttrParam,parameters.get(messageAttrParam)[0]);
            isExists = true;
        }
        return isExists;
    }
}
