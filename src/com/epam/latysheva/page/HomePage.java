package com.epam.latysheva.page;

import com.epam.latysheva.businessObject.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public class HomePage extends Page {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(START_URL);
        return this;
    }

    public InboxPage login(User user) {
        Actions actions = new Actions(driver);
        WebElement loginField = driver.findElement(LOGIN_FIELD);
        WebElement passwordField = driver.findElement(PASSWORD_FIELD);
        WebElement loginButton = driver.findElement(LOGIN_BUTTON);
        waitForElementEnabled(LOGIN_FIELD);
        highlightElementOn(LOGIN_FIELD);
        actions.sendKeys(loginField,user.getLogin()).build().perform();
        highlightElementOff(LOGIN_FIELD);
        waitForElementEnabled(PASSWORD_FIELD);
        highlightElementOn(PASSWORD_FIELD);
        actions.sendKeys(passwordField,user.getPasswword()).build().perform();
        highlightElementOff(PASSWORD_FIELD);
        waitForElementEnabled(LOGIN_BUTTON);
        highlightElementOn(LOGIN_BUTTON);
        actions.click(loginButton).build().perform();
        highlightElementOff(LOGIN_BUTTON);
        return new InboxPage(driver);
    }

    public boolean isHomePage(){
        if (driver.findElement(LOGIN_BUTTON).isEnabled() &
                driver.findElement(LOGIN_FIELD).isEnabled() &
                driver.findElement(PASSWORD_FIELD).isEnabled()){
            return true;
        } else{
            return false;
        }
    }
}
