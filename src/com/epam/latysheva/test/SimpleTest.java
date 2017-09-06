package com.epam.latysheva.test;


import com.epam.latysheva.businessObject.User;
import com.epam.latysheva.page.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SimpleTest {
    private WebDriver driver;

    //Data
    private static final String PATH_TO_GEKODRIVER = "lib/geckodriver.exe";
    private static final String GECKO_DRIVER_SYSTEM_PROPERTY = "webdriver.gecko.driver";
    //protected static final String LOGIN = "lida.test.2017";
    //protected static final String PASSWORD = "$ERDFC5rtfgv";
    protected static final String EMAIL_DETAILS_TO = "lida.test.2017@mail.ru";
    protected static final String EMAIL_DETAILS_SUBJ = "TEST email";
    protected static final String EMAIL_DETAILS_BODY = "Hello, dear!";

    protected static final String CHECK_BODY_IS_THE_SAME_MSG = "FAIL: Body is different";
    protected static final String CHECK_EMAIL_IS_SENT_MSG = "FAIL: Email is not sent";
    protected static final String CHECK_EMAIL_IS_SAVED_MSG = "FAIL: Email is not saved";
    protected static final String CHECK_EMAIL_IS_IN_DRAFTS_MSG = "FAIL: Check that email appears in Drafts failed";
    protected static final String CHECK_EMAIL_IS_NOT_IN_DRAFTS_MSG = "FAIL: Check that email disappears from Drafts failed";
    protected static final String CHECK_EMAIL_IS_IN_SENTS_MSG = "FAIL: Check that email appears in Sents failed";
    protected static final String CHECK_SEND_BTN_PRESENT_FAILED_MSG = "FAIL: Check if Send button is on the page failed";
    protected static final String CHECK_LOGOUT_MSG = "FAIL: Logout was unsuccessful";
    protected static final String CHECK_EMAIL_COUNT_INCREASE_MSG = "FAIL: Email count hasn't been increased by 1";
    protected static final String EMAIL_COUNT_AFTER_REFRESH_MSG = "FAIL: Email count is different after refresh";

    static Logger logger = LogManager.getLogger("TestLogger");

    @BeforeTest
    private void initDriver() throws MalformedURLException {
        /**
         * Set System variable webdriver.gecko.driver.
         */
        System.setProperty(GECKO_DRIVER_SYSTEM_PROPERTY, PATH_TO_GEKODRIVER);
        /**
         * Initialize webdriver.
         * Set pageLoadTimeout and delete all cookies.
         */
        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }


    @Test
    public void firstTest() {
        /**
         * Open home page and login
         */
        logger.info("Open Home Page");
        HomePage homePage = new HomePage(driver);
        logger.info("Login to mailbox");
        InboxPage inboxPage = homePage.open().login(new User());
        /**
         * Wait for Compose button to be clickable and click it.
         */
        inboxPage.waitForElementEnabled(inboxPage.getCOMPOSE_BUTTON());
        logger.info("Click Compose button");
        ComposePage composePage = inboxPage.clickComposeBtn();
        logger.info("Checking redirection to Compose page...");
        Assert.assertTrue(composePage.isComposePage(), CHECK_SEND_BTN_PRESENT_FAILED_MSG);
        /**
         * Fill in  To, Subject, Body fields,
         * save email as draft.
         * Verify that email saving message appears.
         */
        logger.info("Filling in mail information...");
        composePage.fillToField(EMAIL_DETAILS_TO).fillSubjectField(EMAIL_DETAILS_SUBJ).fillBodyField(EMAIL_DETAILS_BODY);
        logger.info("Saving as Draft...");
        composePage.saveAsDraft();
        logger.info("Checking is email saved as Draft...");
        Assert.assertTrue(composePage.isSavedToDrafts(), CHECK_EMAIL_IS_SAVED_MSG);
        /**
         *Go to Draft folder and check
         * that email appears there
         */
        logger.info("Opening Draft folder...");
        DraftsPage draftsPage = composePage.openDrafts();
        logger.info("Checnking that saved email in Daft folder...");
        Assert.assertTrue(draftsPage.isEmailThere(), CHECK_EMAIL_IS_IN_DRAFTS_MSG);
        /**
         * Open saved email and check its body
         */
        logger.info("Checking saved email...");
        composePage = draftsPage.openSavedEmail();
        Assert.assertTrue(composePage.isBodyTheSame(), CHECK_BODY_IS_THE_SAME_MSG);
        /**
         * Send email
         * Check it dissappears from Drafts
         * Check it appears in Setns
         */
        logger.info("Send saved email...");
        MailBoxPage mailBoxPage = composePage.clickSend();
        logger.info("Checking email is sent...");
        Assert.assertTrue(mailBoxPage.isEmailSent(), CHECK_EMAIL_IS_SENT_MSG);
        logger.info("Checking email disappeared from Draft folder...");
        mailBoxPage.openDrafts();
        Boolean tmp = draftsPage.isEmailThere();
        Assert.assertFalse(draftsPage.isEmailThere(), CHECK_EMAIL_IS_NOT_IN_DRAFTS_MSG);
        logger.info("Checking email appeared in Sent folder...");
        SentsPage sentsPage = draftsPage.openSents();
        tmp = sentsPage.isEmailThere();
        Assert.assertTrue(tmp, CHECK_EMAIL_IS_IN_SENTS_MSG);
        /**
         * Logout and check that logout is successful
         */
        logger.info("Logout from the mailbox...");
        homePage = sentsPage.logout();
        Assert.assertTrue(homePage.isHomePage(), CHECK_LOGOUT_MSG);
    }

    /**
     * Scenario 2:
     * 1. Login to the mailbox
     * 2. Verify that login is successful
     * 3. Create a new mail (fill addressee, subject and body fields (addressee should be yourself))
     * 4. Send email
     * 5. Click on Inbox (Входящие)
     * 6. Check that quantity of emails has increased by one
     * 7. Click Delete (Удалить) button
     * 8. Check that message "Удалено 1 письмо. Отменить" has appeared
     * 9. Refresh page
     * 10. Check that amount of emails is same as initially
     * 11. Click on Trash (Корзина)
     * 12. Check that deleted email is actually in Trash
     * 13. Logout
     */
    @Test
    public void secondTest() {
        /**
         * Open home page and login
         */
        HomePage homePage = new HomePage(driver);
        InboxPage inboxPage = homePage.open().login(new User());
        /**
         * Wait for Compose button to be clickable and click it.
         */
        inboxPage.waitForElementEnabled(inboxPage.getCOMPOSE_BUTTON());
        inboxPage.setInitialEmailCount();
        ComposePage composePage = inboxPage.clickComposeBtn();
        Assert.assertTrue(composePage.isComposePage(), CHECK_SEND_BTN_PRESENT_FAILED_MSG);
        /**
         * Fill in  To, Subject, Body fields,
         * send email.
         */
        composePage.fillToField(EMAIL_DETAILS_TO).fillSubjectField(EMAIL_DETAILS_SUBJ).fillBodyField(EMAIL_DETAILS_BODY);
        MailBoxPage mailBoxPage = composePage.clickSend();
        composePage.waitEmailSent();
        Assert.assertTrue(mailBoxPage.isEmailSent(), CHECK_EMAIL_IS_SENT_MSG);
        /**
         * Check that email count is increased by 1
         */
        inboxPage = mailBoxPage.openInbox();
        int newEmailCount = inboxPage.setEmailCount();
        Assert.assertEquals(newEmailCount - inboxPage.getInitialEmailCount(), 1, CHECK_EMAIL_COUNT_INCREASE_MSG);
        /**
         * Check that email is deleted
         */
        inboxPage = mailBoxPage.openInbox();
        inboxPage.selectRecievedEmail();
        inboxPage.clickDelete();
        Assert.assertTrue(inboxPage.isEmailDeletedMoved());
        /**
         * Refresh the page and check that email count hasn't been changed
         */
        int emailCount = inboxPage.setEmailCount();
        driver.navigate().refresh();
        newEmailCount = inboxPage.setEmailCount();
        Assert.assertEquals(newEmailCount, emailCount, EMAIL_COUNT_AFTER_REFRESH_MSG);
        driver.navigate().refresh();
        /**
         * Open Trash and check that email there
         */
        TrashPage trashPage = inboxPage.openTrash();
        Assert.assertTrue(trashPage.isEmailThere());
        /**
         * Logout
         */
        homePage = trashPage.logout();
        Assert.assertTrue(homePage.isHomePage(), CHECK_LOGOUT_MSG);
    }

    /**
     * Scenario 3:
     * 1. Login to the mailbox
     * 2. Verify that login is successful
     * 3. Create a new mail (fill addressee, subject and body fields (addressee should be yourself))
     * 4. Send email
     * 5. Click on Inbox (Входящие)
     * 6. Check that quantity of emails is what was previously plus one
     * 7. Select email that you just sent in Inbox
     * 8. Click Move (Переместить) button
     * 9. Select "Trash" (Корзина) option
     * 10. Check that message "Письмо перемещено в Корзину. Отменить" has appeared
     * 11. Check that amount of emails is same as initially
     * 12. Refresh page
     * 13. Check that amount of emails is same as initially
     * 14. Click on Trash (Корзина)
     * 15. Check that trashed email is actually in Trash
     * 16. Logout
     */
    @Test
    public void thirdTest() {
        /**
         * Open home page and login
         */
        HomePage homePage = new HomePage(driver);
        InboxPage inboxPage = homePage.open().login(new User());
        /**
         * Wait for Compose button to be clickable and click it.
         */
        inboxPage.waitForElementEnabled(inboxPage.getCOMPOSE_BUTTON());
        inboxPage.setInitialEmailCount();
        ComposePage composePage = inboxPage.clickComposeBtn();
        Assert.assertTrue(composePage.isComposePage(), CHECK_SEND_BTN_PRESENT_FAILED_MSG);
        /**
         * Fill in  To, Subject, Body fields,
         * send email.
         */
        composePage.fillToField(EMAIL_DETAILS_TO).fillSubjectField(EMAIL_DETAILS_SUBJ).fillBodyField(EMAIL_DETAILS_BODY);
        MailBoxPage mailBoxPage = composePage.clickSend();
        composePage.waitEmailSent();
        Assert.assertTrue(mailBoxPage.isEmailSent(), CHECK_EMAIL_IS_SENT_MSG);
        inboxPage = mailBoxPage.openInbox();
        int newEmailCount = inboxPage.setEmailCount();
        Assert.assertEquals(newEmailCount - inboxPage.getInitialEmailCount(), 1, CHECK_EMAIL_COUNT_INCREASE_MSG);
        /**
         * Move email to trash and check it
         */
        inboxPage = mailBoxPage.openInbox();
        inboxPage.selectRecievedEmail();
        inboxPage.clickMoveToTrash();
        Assert.assertTrue(inboxPage.isEmailDeletedMoved());
        /**
         * Refresh the page and check that email count hasn't been changed
         */
        int emailCount = inboxPage.setEmailCount();
        driver.navigate().refresh();
        newEmailCount = inboxPage.setEmailCount();
        Assert.assertEquals(newEmailCount, emailCount, EMAIL_COUNT_AFTER_REFRESH_MSG);
        /**
         * Open Trash and check that email there
         */
        TrashPage trashPage = inboxPage.openTrash();
        try {
            wait(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(trashPage.isEmailThere());
        /**
         * Logout
         */
        homePage = trashPage.logout();
        Assert.assertTrue(homePage.isHomePage(), CHECK_LOGOUT_MSG);
    }

    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            System.out.println(testResult.getStatus());
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("errorScreenshots\\" + testResult.getName() + "-"
                    + Arrays.toString(testResult.getParameters()) + ".jpg"));
        }
    }

    @AfterTest
    private void closeDeriver() {
        driver.quit();
        /**
         *Kill all geckodriver.exe processes
         */
        boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments()
                        .toString().indexOf("-agentlib:jdwp") > 0;
        try {
            if (isDebug)
                Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
