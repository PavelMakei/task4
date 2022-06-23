package by.makei.shop.command;

public class Router {
    public enum Type {
        FORWARD,
        REDIRECT
    }

    private String currentPage = PagePath.INDEX;
    private Type currentType = Type.FORWARD;//значение по умолчанию, т.к. наиболее часто применимо

    public Router() {
    }

    public Router(Type currentType, String currentPage) {
        this.currentType = currentType;
        this.currentPage = currentPage;
    }

    public Router(String currentPage) {
        this.currentPage = currentPage;
    }

    public Router(Type currentType) {
        this.currentType = currentType;
    }

    public Type getCurrentType() {
        return currentType;
    }

    public void setRedirectType() {
        this.currentType = Type.REDIRECT;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }


}
