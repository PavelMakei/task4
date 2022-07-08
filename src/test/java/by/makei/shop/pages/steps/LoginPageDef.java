package by.makei.shop.pages.steps;

import by.makei.shop.pages.LogInPage;
import io.cucumber.java.en.Then;

import static by.makei.shop.pages.config.UserConfig.*;


public class LoginPageDef {
    LogInPage logInPage = new LogInPage();

    @Then("Input user login")
    public void inputUserLogin() {
        logInPage.inputLogin(TEST_USER_LOGIN);
    }

    @Then("Input user password")
    public void inputUserPassword() {
        logInPage.inputPassword(TEST_USER_PASSWORD);
    }

    @Then("Input admin login")
    public void inputAdminLogin() {
        logInPage.inputLogin(TEST_ADMIN_LOGIN);
    }

    @Then("Input admin password")
    public void inputAdminPassword() {
        logInPage.inputPassword(TEST_ADMIN_PASSWORD);
    }

    @Then("Input blocked login")
    public void inputBlockedLogin() {
        logInPage.inputLogin(TEST_BLOCKED_LOGIN);
    }

    @Then("Input blocked password")
    public void inputBlockedPassword() {
        logInPage.inputPassword(TEST_BLOCKED_PASSWORD);
    }
}
