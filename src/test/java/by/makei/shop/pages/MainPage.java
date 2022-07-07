package by.makei.shop.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.*;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    public void clickButtonId(String buttId) {
        $(By.id(buttId)).click();
    }
    public void clickButtonXpath(String buttXpath) {
        SelenideElement button = $(By.xpath(buttXpath));
        button.click();
    }
    public void contentIsVisible(String str){
        $(By.xpath(("//*[contains(text(),'" + str + "')]"))).shouldBe(Condition.visible);
    }

    public void chooseOption(String id, String value){
        SelenideElement dropDown = $(By.id(id));
        dropDown.selectOptionByValue(value);

        //TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

}
