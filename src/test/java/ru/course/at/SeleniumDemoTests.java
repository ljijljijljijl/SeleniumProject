package ru.course.at;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumDemoTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        driver.get("https://www.bing.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public void clickElement(List<WebElement> searchResults, int num) {
        searchResults.get(num).click();
    }

    @Test
    public void searchFieldTest() {
        String input = "Selenium";
        By searchFieldCss = By.cssSelector("#sb_form_q");
        WebElement searchField = driver.findElement(searchFieldCss);
        searchField.sendKeys(input);
        searchField.submit();

        WebElement searchPageField = driver.findElement(searchFieldCss);
        assertEquals(input, searchPageField.getAttribute("value"), "Текст не совпадает");
    }

    @Test
    public void resultsLinkTest() {
        String input = "Selenium";
        String seleniumUrl = "https://www.selenium.dev/";
        By searchFieldCss = By.cssSelector("#sb_form_q");
        By relevantLink = By.xpath("//a[contains(@class, 'tilk')]//cite[contains(., 'selenium.dev')]");
        WebElement searchField = driver.findElement(searchFieldCss);
        searchField.sendKeys(input);
        searchField.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.elementToBeClickable(relevantLink));
        List<WebElement> searchResults = driver.findElements(relevantLink);
        clickElement(searchResults, 0);

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.urlToBe(seleniumUrl));
        assertEquals(seleniumUrl, driver.getCurrentUrl(),
                "Адрес страницы не совпадает с ожидаемым");
    }
}
