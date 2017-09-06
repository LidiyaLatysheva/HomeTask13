package com.epam.latysheva.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.util.List;


public class MailBoxPage extends Page {
    protected static String saveTime;
    protected static int emailCount;
    protected static String receivedTime;
    protected List<String> emailTimeList;
    private static final String SENT_OK_URL = "sendmsgok";
    protected final static By EMAIL_IN_LIST = By.xpath("//div[@style='']/div[contains(@class, 'b-datalist_letters')]//a[@class=\"js-href b-datalist__item__link\"]");

    public MailBoxPage(WebDriver driver) {
        super(driver);
    }

    public DraftsPage openDrafts() {
        highlightElementOn(DRAFT_LINK);
        driver.findElement(DRAFT_LINK).click();
        highlightElementOff(DRAFT_LINK);
        return new DraftsPage(driver);
    }

    public SentsPage openSents() {
        highlightElementOn(SENT_LINK);
        driver.findElement(SENT_LINK).click();
        highlightElementOff(SENT_LINK);
        return new SentsPage(driver);
    }

    public InboxPage openInbox() {
        driver.findElement(INBOX_LINK).click();
        return new InboxPage(driver);
    }

    public TrashPage openTrash() {
        driver.findElement(TRASH_LINK).click();
        return new TrashPage(driver);
    }

    public boolean isEmailSent() {
        waitForElementEnabled(SENT_EMAIL_CONFIRMATION);
        String currentURL = driver.getCurrentUrl();
        return currentURL.contains(SENT_OK_URL);
    }

    public HomePage logout() {
        highlightElementOn(LOGOUT_LINK);
        driver.findElement(LOGOUT_LINK).click();
        highlightElementOff(LOGOUT_LINK);
        return new HomePage(driver);
    }

    public MailBoxPage clickDelete() {
        driver.findElement(DELETE_BUTTON).click();
        return this;
    }

    public boolean isEmailDeletedMoved() {
        try {
            waitForElementVisible(DELETE_MOVE_EMAIL_CONFIRM_MESSAGE);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<String> getEmailTimes() {
        int size = driver.findElements(By.xpath(EMAIL_TIME)).size();
        for (int i = 0; i < size; i++) {
            emailTimeList.add(i, driver.findElements(By.xpath(EMAIL_TIME)).get(i).getText());
        }
        return emailTimeList;
    }

    public MailBoxPage clickMoveToTrash() {
        boolean tmp =driver.findElement(MOVE_BUTTON).isDisplayed();
        if (driver.findElement(MOVE_BUTTON).isDisplayed()){
        driver.findElement(MOVE_BUTTON).click();
        driver.findElement(TRASH_MENU_ITEM).click();}
        return this;
    }

    public boolean isEmailThere() {
        try {
            String str = PART_EMAIL_LOCATOR;
            //waitForElementVisible(By.xpath(PART_EMAIL_LOCATOR));
            waitForElementEnabled(By.xpath(PART_EMAIL_LOCATOR));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
