package com.epam.latysheva.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;


public class InboxPage extends MailBoxPage {

/*
    private static final By DRAFT_LINK = By.cssSelector("a[href='/messages/drafts/']");
    private static final By SENT_LINK = By.cssSelector("a[href='/messages/sent/']");
    private static final By LOGOUT_LINK = By.id("PH_logoutLink");
    protected static final By COMPOSE_BUTTON = By.cssSelector("a[data-name='compose']");
*/


    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public HomePage logout() {
        highlightElementOn(LOGOUT_LINK);
        driver.findElement(LOGOUT_LINK).click();
        highlightElementOff(LOGOUT_LINK);
        return new HomePage(driver);
    }

    public ComposePage clickComposeBtn() {
        highlightElementOn(COMPOSE_BUTTON);
        driver.findElement(COMPOSE_BUTTON).click();
        highlightElementOff(COMPOSE_BUTTON);
        return new ComposePage(driver);
    }

    public By getCOMPOSE_BUTTON() {
        return COMPOSE_BUTTON;
    }

    public void setInitialEmailCount() {
        emailCount = setEmailCount();
    }

    public int getInitialEmailCount() {
        return emailCount;
    }

    public int setEmailCount() {
        return driver.findElements(EMAIL_IN_LIST).size();
    }

    public InboxPage selectRecievedEmail(){
        String tmp = EMAIL_CHECKBOX;
        highlightElementOn(By.xpath(tmp));
        driver.findElement(By.xpath(tmp)).click();
        highlightElementOff(By.xpath(tmp));
        return this;
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

