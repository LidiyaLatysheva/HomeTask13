package com.epam.latysheva.page;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class SentsPage extends MailBoxPage {

    public SentsPage(WebDriver driver) {
        super(driver);
    }

/*    public boolean isEmailThere() {
        try {
            waitForElementVisible(By.xpath(PART_EMAIL_LOCATOR));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }*/
}
