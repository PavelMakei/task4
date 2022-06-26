package by.makei.shop.command;

import java.util.Set;

public enum PagePath {
    ;
    public static final String ABOUT = "/view/pages/common/about.jsp";
    public static final String ADD_NEW_PRODUCT = "/view/pages/admin/addnewproduct.jsp";
    public static final String BLOCKED_USER = "/view/pages/common/blockeduser.jsp";
    public static final String CHECKOUT = "/view/pages/user/checkout.jsp";
    public static final String DEPOSIT_MONEY = "/view/pages/user/depositmoney.jsp";
    public static final String ERROR403 = "/view/error/error403.jsp";
    public static final String ERROR404 = "/view/error/error404.jsp";
    public static final String ERROR500 = "/view/error/error500.jsp";
    public static final String INDEX = "/index.jsp";
    public static final String LOGINATION = "/view/pages/common/login.jsp";
    public static final String MAIN = "/view/pages/common/main.jsp";
    public static final String ORDERS = "/view/pages/admin/orders.jsp";
    public static final String PASSWORD_RECOVERY = "/view/pages/user/passwordrecovery.jsp";
    public static final String REGISTRATION = "/view/pages/common/registration.jsp";
    public static final String ROOT = "/";
    public static final String SHOW_CART = "/view/pages/common/showcart.jsp";
    public static final String SHOW_PRODUCT = "/view/pages/common/showproduct.jsp";
    public static final String UPDATE_PRODUCT = "/view/pages/admin/updateproduct.jsp";
    public static final String UPDATE_PROFILE = "/view/pages/user/updateprofile.jsp";
    public static final String USERS = "/view/pages/admin/users.jsp";
    //redirect get forward
    public static final String GO_TO_ADD_NEW_PRODUCT = "/controller?command=go_to_add_new_product";
    public static final String GO_TO_MAIN = "/controller?command=go_to_main";
    public static final String GO_TO_SHOW_CART = "/controller?command=go_to_show_cart";
    public static final String GO_TO_SHOW_ORDERS = "/controller?command=show_order";
    public static final String GO_TO_UPDATE_PRODUCT = "/controller?command=go_update_product";

    //page security
    public static final Set<String> adminPages;
    public static final Set<String> userPages;
    public static final Set<String> guestPages;
    public static final Set<String> blockedPages;

    static {
        adminPages = Set.of(
                ABOUT,
                ADD_NEW_PRODUCT,
                CHECKOUT,
                DEPOSIT_MONEY,
                ERROR403,
                ERROR404,
                ERROR500,
                INDEX,
                MAIN,
                ORDERS,
                ROOT,
                SHOW_CART,
                SHOW_PRODUCT,
                UPDATE_PRODUCT,
                UPDATE_PROFILE,
                USERS);
        userPages = Set.of(
                ABOUT,
                CHECKOUT,
                DEPOSIT_MONEY,
                ERROR403,
                ERROR404,
                ERROR500,
                INDEX,
                MAIN,
                ORDERS,
                ROOT,
                SHOW_CART,
                SHOW_PRODUCT,
                UPDATE_PROFILE
        );
        guestPages = Set.of(
                ABOUT,
                ERROR403,
                ERROR404,
                ERROR500,
                INDEX,
                LOGINATION,
                MAIN,
                PASSWORD_RECOVERY,
                REGISTRATION,
                ROOT,
                SHOW_CART,
                SHOW_PRODUCT);
        blockedPages = Set.of(
                ABOUT,
                INDEX,
                BLOCKED_USER,
                ROOT
        );
    }
}
