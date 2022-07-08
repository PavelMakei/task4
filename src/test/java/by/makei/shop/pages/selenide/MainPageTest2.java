package by.makei.shop.pages.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;


public class MainPageTest2 {

    @BeforeAll
    public static void init() {
        Configuration.browser = "firefox";

    }

    @Test
    public void checkIfOnAboutPage() {
        open("http://localhost:8080/");
        $(By.id("aboutRef")).click();
        $("title").shouldHave(attribute("text", "О нас"));
    }

    @Test
    public void checkIfLanguageSwitch() {
        open("http://localhost:8080/");
        $(By.id("languageBtn")).click();
        $("title").shouldHave(attribute("text", "Main"));
        $(By.id("languageBtn")).click();
        $("title").shouldHave(attribute("text", "Главная"));
    }

    @Test
    public void checkIfCartPage() {
        open("http://localhost:8080/");
        $(By.id("ShowCartBtn")).click();
        switchTo().window(1);
        $("title").shouldHave(attribute("text", "Продукты в корзине"));
    }

    @Test
    public void checkIfCurrencyWorks() {
        SelenideElement rate = $(By.id("rate"));
        open("http://localhost:8080/");
        String oldValue = rate.getText();
        $(By.id("currency")).selectOption("EUR");
        String newValue = rate.getText();
        Assertions.assertNotEquals(newValue, oldValue);
    }

    @Test
    public void checkIfLoginPage() {
        open("http://localhost:8080/");
        $(By.id("logInBtn")).click();

        $("title").shouldHave(attribute("text", "Логин"));
    }

    @AfterAll
    static void teardown() {
        closeWebDriver();
    }

}
