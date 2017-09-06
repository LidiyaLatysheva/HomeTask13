package com.epam.latysheva.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.internal.PackageUtils;


public class Page {

    private static int WAIT_CONST = 10;
    protected WebDriver driver;

    //Data
    protected static final String START_URL = "https://mail.ru/";

    protected static final String EMAIL_DETAILS_TO = "lida.test.2017@mail.ru";
    protected static final String EMAIL_DETAILS_TO_CUT = "lida.test.2017";
    protected static final String EMAIL_DETAILS_SUBJ = "TEST email";
    protected static final String EMAIL_DETAILS_BODY = "Hello, dear!";
    protected static final String EMAIL_SIGNATURE = "\n\n\n-Thank you!";
    protected static final String SAVED_TO_DRAFT_MSG = "Сохранено в черновиках в";

    //Locators:
    protected static final By LOGIN_FIELD = By.id("mailbox__login");
    protected static final By PASSWORD_FIELD = By.id("mailbox__password");
    protected static final By LOGIN_BUTTON = By.id("mailbox__auth__button");
    protected static final By COMPOSE_BUTTON = By.cssSelector("a[data-name='compose']");
    protected static final By SEND_BUTTON = By.xpath("//*[@data-name=\"send\"]");
    protected static final By DELETE_BUTTON = By.xpath("//*[@data-name=\"remove\"]");
    //protected static final By MOVE_BUTTON=By.xpath("//*[contains(@data-title,\"Переместить\")]");


    protected static final By MOVE_BUTTON=By.xpath("//div[@style=\"\"]//*[@data-shortcut-title=\"V\"]");

    protected static final By TRASH_MENU_ITEM = By.xpath("//div[@style=\"\"]//a[@data-text=\"Корзина\"]");



    protected static final By TO_FIELD = By.xpath("//textarea[@data-original-name=\"To\"]");
    protected static final By SUBJECT_FIELD = By.name("Subject");
    protected static final By BODY_FIELD_IFRAME = By.xpath("//iframe[contains(@id, 'composeEditor')]");
    protected static final By BODY_FIELD = By.id("tinymce");
    protected static final By SAVE_MORE_DROPDOWN = By.xpath("//div[@data-group=\"save-more\"]");
    protected static final By SAVE_DRAFT_MENU_ITEM = By.cssSelector("a[data-name='saveDraft']");
    protected static final By SAVE_DRAFT_BUTTON=By.xpath("//*[@data-name=\"saveDraft\"]");
    protected static final By SAVE_STATUS = By.xpath("//div[@class='b-toolbar__message' and @data-mnemo=\"saveStatus\"]");
    protected static final By DRAFT_LINK = By.cssSelector("a[href='/messages/drafts/']");
    protected static final By SENT_LINK = By.cssSelector("a[href='/messages/sent/']");
    protected static final By INBOX_LINK = By.cssSelector("a[href='/messages/inbox/']");
    protected static final By TRASH_LINK = By.cssSelector("a[href='/messages/trash/']");
    protected static final String EMAIL = "//a[@data-subject=\"" + EMAIL_DETAILS_SUBJ + "\" and contains(@data-title,\"" + EMAIL_DETAILS_TO_CUT + "\")]//span[@title=\"Сегодня, ";
    protected static final String PART_EMAIL_LOCATOR = "//a[@class=\"js-href b-datalist__item__link\" and @data-subject=\"" + EMAIL_DETAILS_SUBJ + "\" and contains(@title,\"" + EMAIL_DETAILS_TO + "\")]";
    protected static final String EMAIL_CHECKBOX = PART_EMAIL_LOCATOR + "//div[@class=\"b-checkbox__box\"]";
    protected static final String EMAIL_TIME = PART_EMAIL_LOCATOR+"//span[contains(@title,\"Сегодня\")]";
    protected static final By LOGOUT_LINK = By.id("PH_logoutLink");
    protected static final By COMPOSE_EDITOR_IFRAME = By.xpath("//iframe[contains(@id, 'composeEditor')]");
    protected static final By COMPOSE_EDITOR = By.id("tinymce");
    protected static final By DELETE_MOVE_EMAIL_CONFIRM_MESSAGE = By.xpath(".//div[@style=\"\"]//div[@class=\"notify-message__title\"]//span[@class='js-txt _js-title notify-message__title__text notify-message__title__text_ok']");
    protected static final By SENT_EMAIL_CONFIRMATION = By.id("b-compose__sent");

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElementPresents(By locator){
        new WebDriverWait(driver, WAIT_CONST).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForElementEnabled(By locator){
        new WebDriverWait(driver, WAIT_CONST).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForElementVisible(By locator){
        //locator = By.xpath(".//div[@style=\"\"]//div[@class=\"notify-message__title\"]//span[@class='js-txt _js-title notify-message__title__text notify-message__title__text_ok']");
        new WebDriverWait(driver, WAIT_CONST).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void highlightElementOn(By locator){
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border = '4px solid green'",driver.findElement(locator));
    }

    public void highlightElementOff(By locator){
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border = '0px'",driver.findElement(locator));
    }

}
