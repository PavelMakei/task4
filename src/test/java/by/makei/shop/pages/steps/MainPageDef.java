package by.makei.shop.pages.steps;

import by.makei.shop.pages.MainPage;
import io.cucumber.java.en.Then;

public class MainPageDef {

    MainPage mainPage = new MainPage();

    @Then("Click {string} button")
    public void clickButton(String arg0) {
        mainPage.clickButtonId(arg0);
    }

    @Then("Click button enter")
    public void clickButtonSubmit() {
        mainPage.clickButtonXpath("//*[@id=\"enterBtn\"]");
    }

    @Then("Content with {string} visible")
    public void contentWithVisible(String arg0) {
        mainPage.contentIsVisible(arg0);
    }
}
