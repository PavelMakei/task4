package by.makei.shop.pages.selenide;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainPageTestSelenium {
    private static final Logger logger = LogManager.getLogger();
    private static FirefoxDriver driver;

    @BeforeAll
    static void init() {
        System.setProperty("webdriver.gecko.driver", "E:/Selenium/geckodriver/geckodriver.exe");
    }

    @BeforeEach
    void start(){
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/");
    }

    @Test
    void goToAbout() {
        WebElement aboutRef = driver.findElement(By.id("aboutRef"));
        aboutRef.click();
        String expected = "О нас";
        String actual = driver.getTitle();
        logger.log(Level.DEBUG,"text = {}",actual);
        assertEquals(expected,actual);
    }

    @Test
    void changeLanguage() {
        WebElement aboutRef = driver.findElement(By.id("languageBtn"));
        aboutRef.click();
        String expected = "Main";
        String actual = driver.getTitle();
        logger.log(Level.DEBUG,"text = {}",actual);
        assertEquals(expected,actual);
    }

    @AfterAll
    static void shutdown(){
        driver.quit();
    }

}

