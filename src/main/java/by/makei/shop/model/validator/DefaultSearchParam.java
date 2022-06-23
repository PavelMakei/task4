package by.makei.shop.model.validator;

import static by.makei.shop.command.AttributeName.ORDER_BY_PRICE_UP;

public enum DefaultSearchParam {
    ;
    public static final String DEFAULT_BRAND_ID ="0";
    public static final String DEFAULT_TYPE_ID ="0";
    public static final String DEFAULT_MIN_PRICE ="0";
    public static final String DEFAULT_MAX_PRICE ="10000";
    public static final String DEFAULT_MIN_POWER ="0";
    public static final String DEFAULT_MAX_POWER ="999";
    public static final String DEFAULT_PAGE ="1";
    public static final String DEFAULT_PAGE_BUTTON ="search_button";
    public static final int PRODUCTS_ON_PAGE = 4;
    public static final String DEFAULT_ORDER_BY = ORDER_BY_PRICE_UP;
    public static final String DEFAULT_IN_STOCK = "0";

}
