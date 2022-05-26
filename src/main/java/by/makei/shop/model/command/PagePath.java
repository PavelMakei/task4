package by.makei.shop.model.command;

public enum PagePath {
    ;
    public static final String INDEX = "index.jsp";
    public static final String LOGINATION = "view/pages/login.jsp";
    public static final String MAIN = "view/pages/main.jsp";
    public static final String ERROR500 = "view/error/error500.jsp";
    public static final String REGISTRATION = "view/pages/registration.jsp";
    public static final String PASSWORD_RECOVERY = "view/pages/user/passwordrecovery.jsp";
    public static final String UPDATE_PROFILE = "view/pages/user/updateprofile.jsp";
    public static final String ADD_NEW_PRODUCT = "view/pages/admin/addnewproduct.jsp";
    public static final String UPDATE_PRODUCT = "view/pages/admin/updateproduct.jsp";
    public static final String SHOW_PRODUCT = "view/pages/showproduct.jsp";
    public static final String USERS = "view/pages/admin/users.jsp";


    public static final String GO_TO_ADD_NEW_PRODUCT = "/controller?command=go_to_add_new_product";
    public static final String GO_TO_UPDATE_PRODUCT = "/controller?command=go_update_product";
    public static final String GO_TO_MAIN = "/controller?command=go_to_main";




    public static final String TEMP = "view/pages/temp.jsp";

}
