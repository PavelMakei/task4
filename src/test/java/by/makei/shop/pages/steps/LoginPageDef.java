package by.makei.shop.pages.steps;

import by.makei.shop.pages.LogInPage;
import io.cucumber.java.en.Then;

import static by.makei.shop.pages.config.UserConfig.USER_LOGIN;
import static by.makei.shop.pages.config.UserConfig.USER_PASSWORD;


public class LoginPageDef {
    LogInPage logInPage = new LogInPage();

    @Then("Input login")
    public void inputLogin() {
        logInPage.inputLogin(USER_LOGIN);
    }

    @Then("Input password")
    public void inputPassword() {
        logInPage.inputPassword(USER_PASSWORD);
    }
}
