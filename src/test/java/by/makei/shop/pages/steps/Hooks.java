package by.makei.shop.pages.steps;


import com.codeborne.selenide.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class Hooks {
    @Before
    public void openUrl(){
        Configuration.browser = "firefox";
        open("http://localhost:8080/");
    }

    @After
    public void close(){
        closeWebDriver();
    }
}
