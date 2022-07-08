package by.makei.shop.pages;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class DepositMoneyPage {
    private static final Logger logger = LogManager.getLogger();
    static double currentAmount;

    public void initCurrentAmount() {
        currentAmount = Double.valueOf($(By.id("current_balance")).val());
        logger.log(Level.INFO,currentAmount);
    }
    public void inputDataById(String inputFieldId, String inputValue) {
        $(By.id(inputFieldId)).val(inputValue);
    }

    public static double getCurrentAmount() {
        return currentAmount;
    }

    public void clickButtonById(String btn_id) {
        $(By.id(btn_id)).click();
    }

}
