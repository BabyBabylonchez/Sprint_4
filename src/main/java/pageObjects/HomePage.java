package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver webDriver;

    public HomePage(WebDriver driver) {
        this.webDriver = driver;
    }

    // элемент аккордеона - заголовок
    private final By accordionHeader = By.className("accordion__heading");
    // элемент аккордеона - описание
    private final By accordionDescription = By.xpath(".//div[@class='accordion__panel']/p");
    // кнопка 'заказать' в шапке сайта
    private final By orderButtonHeader = By.xpath(".//div[starts-with(@class, 'Header_Nav')]//button[starts-with(@class, 'Button_Button')]");
    // кнопка 'заказать' в теле сайта
    private final By orderButtonBody = By.xpath(".//div[starts-with(@class, 'Home_RoadMap')]//button[starts-with(@class, 'Button_Button')]");
    //кнопка 'принять куки'
    private final By cookieAcceptButton = By.id("rcc-confirm-button");

    //метод нажатия на кнопку 'принять куки
    public void clickAcceptCookieButton() {
        this.webDriver.findElement(this.cookieAcceptButton).click();
    }

    //метод ожидания загрузки раскрывающегося описания аккордеона
    public void waitForLoadItem(int index) {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElements(this.accordionDescription).get(index)));
    }

    //метод получения текста из заголовка аккордеона
    public String getAccordionHeaderText(int index) {
        return this.webDriver.findElements(this.accordionHeader).get(index).getText();
    }

    //метод нажатия на элемент аккордеона
    //перед нажатием идет ожидание пока элемент не станет кликабельным
    public void clickAccordionHeader(int index) {
        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));

        WebElement header = wait.until(ExpectedConditions.elementToBeClickable(this.webDriver.findElements(this.accordionHeader).get(index)));
        header.click();
    }
    //метод проверяющий отображение элемента аккордеона
    public boolean isAccordionItemDisplayed(int index) {
        return this.webDriver.findElements(this.accordionDescription).get(index).isDisplayed();
    }

    //метод нажатия на кнопку оформления заказа в шапке
    public void clickOrderButtonHeader() {
        this.webDriver.findElement(this.orderButtonHeader).click();
    }

    //етод нажатия на кнопку оформления заказа в теле сайта
    public void clickOrderButtonBody() {
        this.webDriver.findElement(this.orderButtonBody).click();
    }

    public void scrollIntoAccordionHeader() {
        WebElement headerElement = this.webDriver.findElement(accordionHeader);
        ((JavascriptExecutor)this.webDriver).executeScript("arguments[0].scrollIntoView();", headerElement);
    }
}
