package by.makei.shop.pages.steps;

import by.makei.shop.pages.DepositMoneyPage;
import com.codeborne.selenide.Condition;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;

import static by.makei.shop.pages.config.UserConfig.*;
import static com.codeborne.selenide.Selenide.$;

public class DepositMoneyPageDef {
    private DepositMoneyPage depositPage = new DepositMoneyPage();


    @Then("Save current amount")
    public void saveCurrentAmount() {
        depositPage.initCurrentAmount();
    }

    @Then("Input card number")
    public void inputCardNumber() {
        depositPage.inputDataById("card_number", TEST_CARD_NUMBER);
    }

    @And("Input card expiration date")
    public void inputCardExpirationDate() {
        depositPage.inputDataById("card_exp_date", TEST_CARD_EXP_DATE);
    }

    @And("Input card CVC")
    public void inputCardCVC() {
        depositPage.inputDataById("card_cvc", TEST_CARD_CVC);
    }

    @And("Input Name on card")
    public void inputNameOnCard() {
        depositPage.inputDataById("card_holder", TEST_NAME_ON_CARD);
    }

    @And("Input amount")
    public void inputAmount() {
        depositPage.inputDataById("amount_to_deposit", TEST_AMOUNT_TO_DEPOSIT);
    }

    @And("Click deposit button")
    public void clickDepositButton() {
        depositPage.clickButtonById("deposit_btn");
    }

    @Then("Content increased amount visible")
    public void contentIncreasedAmountVisible() {
        Double expectedAmount = depositPage.getCurrentAmount() + Double.parseDouble(TEST_AMOUNT_TO_DEPOSIT);
        String expected = String.valueOf(expectedAmount);
        $(By.xpath(("//input[@value='" + expected + "']"))).shouldBe(Condition.visible);
    }
}
