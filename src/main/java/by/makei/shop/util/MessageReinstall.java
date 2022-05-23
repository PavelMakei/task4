package by.makei.shop.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class MessageReinstall {
    private MessageReinstall(){}

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
