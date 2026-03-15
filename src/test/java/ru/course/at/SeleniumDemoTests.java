package ru.course.at;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.course.at.MyWait.myWait;

public class SeleniumDemoTests {

    private static WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        driver.get("https://www.duckduckgo.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    String input = "Selenium";
    By searchFieldCss = By.cssSelector("input[class*='search-input']");
    String seleniumUrl = "https://www.selenium.dev/";

    public void clickElement(List<WebElement> searchResults, int num) {
        searchResults.get(num).click();
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @Test
    public void searchFieldTest() {
        WebElement searchField = driver.findElement(searchFieldCss);
        searchField.sendKeys(input);
        searchField.submit();

        WebElement searchPageField = driver.findElement(By.cssSelector("#search_form_input"));
        assertEquals(input, searchPageField.getAttribute("value"),
                "Текст не совпадает");
    }

    @Test
    public void resultsLinkTest() {
        By relevantLink = By.cssSelector("#r1-0 [data-testid='result-extras-url-link']");
        WebElement searchField = driver.findElement(searchFieldCss);
        searchField.sendKeys(input);
        searchField.submit();

        myWait(6).clickable(relevantLink);
        List<WebElement> searchResults = driver.findElements(relevantLink);
        clickElement(searchResults, 0);

        assertEquals(seleniumUrl, driver.getCurrentUrl(),
                "Первый отображаемый сайт на странице не https://www.selenium.dev/");
    }
}
