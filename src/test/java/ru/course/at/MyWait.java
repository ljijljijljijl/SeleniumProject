package ru.course.at;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyWait {
    private WebDriverWait wait;
    public int secondsToWait;

    public static MyWait myWait(int seconds) {
        return new MyWait(seconds);
    }

    public MyWait(int seconds) {
        secondsToWait = seconds;
        wait = new WebDriverWait(SeleniumDemoTests.getDriver(), Duration.ofSeconds(seconds));
    }

    public WebElement clickable(By element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
