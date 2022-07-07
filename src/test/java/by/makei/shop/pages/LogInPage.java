package by.makei.shop.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LogInPage {

    private SelenideElement loginInput = $(By.id("login"));
    private SelenideElement passwordInput = $(By.id("password"));

    public void inputLogin(String text) {
        loginInput.val(text);
    }

    public void inputPassword(String text) {
        passwordInput.val(text);
    }
}
