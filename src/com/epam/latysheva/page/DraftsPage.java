package com.epam.latysheva.page;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class DraftsPage extends MailBoxPage {
    private String emailLocator;

    public DraftsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isEmailThere() {
        try {
            emailLocator = EMAIL + saveTime + "\"]";
            waitForElementVisible(By.xpath(emailLocator));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }catch (TimeoutException e){
            return false;
        }
    }

    public ComposePage openSavedEmail() {
        if (isEmailThere()) {
            highlightElementOn(By.xpath(emailLocator));
            driver.findElement(By.xpath(emailLocator)).click();
            highlightElementOff(By.xpath(emailLocator));
            return new ComposePage(driver);
        }
        return new ComposePage(driver);
    }
}
