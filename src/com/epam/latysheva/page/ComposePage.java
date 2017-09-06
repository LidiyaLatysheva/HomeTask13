package com.epam.latysheva.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;


public class ComposePage extends MailBoxPage {

    static Logger logger = LogManager.getLogger("Logger");

    public ComposePage(WebDriver driver) {
        super(driver);
    }

    public ComposePage fillToField(String toEmail) {
        waitForElementEnabled(TO_FIELD);
        driver.findElement(TO_FIELD).sendKeys(toEmail);
        return this;
    }

    public ComposePage fillSubjectField(String subject) {
        waitForElementEnabled(SUBJECT_FIELD);
        highlightElementOn(SUBJECT_FIELD);
        driver.findElement(SUBJECT_FIELD).sendKeys(subject);
        highlightElementOff(SUBJECT_FIELD);
        return this;
    }

    public ComposePage fillBodyField(String body) {
        driver.switchTo().frame(driver.findElement(BODY_FIELD_IFRAME));
        highlightElementOn(BODY_FIELD);
        driver.findElement(BODY_FIELD).sendKeys(body);
        highlightElementOff(BODY_FIELD);
        driver.switchTo().defaultContent();
        return this;
    }

    /**
     * Save email as Draft.
     */
    public ComposePage saveAsDraft() {
        /*driver.findElement(SAVE_MORE_DROPDOWN).click();
        driver.findElement(SAVE_DRAFT_MENU_ITEM).click();
        */
        highlightElementOn(SAVE_DRAFT_BUTTON);
        driver.findElement(SAVE_DRAFT_BUTTON).click();
        highlightElementOff(SAVE_DRAFT_BUTTON);
        return this;
    }

    /**
     * Check that Email saved message appears.
     */
    public boolean isSavedToDrafts() {
        waitForElementVisible(SAVE_STATUS);
        highlightElementOn(SAVE_STATUS);
        String str = driver.findElement(SAVE_STATUS).getText();
        boolean isContained = str.contains(SAVED_TO_DRAFT_MSG);
        /**
         * Parsing saved message to get saving time for further verification.
         */
        String[] tmpWords = str.split("\\s");
        int size = tmpWords.length;
        saveTime = tmpWords[size - 1].toString();
        if (!isContained){
            logger.error("Email is not saved as draft");
        } else {
            logger.info("Email is saved as draft");
        }
        highlightElementOff(SAVE_STATUS);
        return isContained;
    }

    /**
     * Check that email body is the same as entered.
     */
    public boolean isBodyTheSame() {
        driver.switchTo().frame(driver.findElement(COMPOSE_EDITOR_IFRAME));
        waitForElementVisible(COMPOSE_EDITOR);
        String bodyField = driver.findElement(COMPOSE_EDITOR).getText();
        driver.switchTo().defaultContent();
        String str = EMAIL_DETAILS_BODY + EMAIL_SIGNATURE;
        return bodyField.equals(str);
    }

    public boolean isComposePage(){
        if (driver.findElement(SEND_BUTTON).isEnabled()){
            logger.info("isComposePage() true");
            return true;
        } else{
            logger.error("It is not Compose page: Send button i not enabled.");
            return false;
        }
    }

    /**
     * Click Send button
     */
    public MailBoxPage clickSend(){
        waitForElementEnabled(SEND_BUTTON);
        highlightElementOn(SEND_BUTTON);
        driver.findElement(SEND_BUTTON).click();
        highlightElementOff(SEND_BUTTON);
        return new MailBoxPage(driver);
    }

    public void waitEmailSent(){
        waitForElementPresents(SENT_EMAIL_CONFIRMATION);
    }
}
