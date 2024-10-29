package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {
    // форма заказа
    private final By orderForm = By.xpath(".//div[starts-with(@class, 'Order_Form')]");
    // имя
    private final By nameInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Имя')]");
    // фамилия
    private final By surnameInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Фамилия')]");
    // адрес
    private final By addressInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Адрес')]");
    // станция метро
    private final By metroInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Станция метро')]");
    //обертка списка станций метро
    private final By metroList = By.className("select-search__select");
    //список доступных станций метро
    private final By metroListItems = By.xpath(".//div[@class='select-search__select']//div[starts-with(@class,'Order_Text')]");
    // телефон
    private final By phoneInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Телефон')]");

    // кнопка 'далее'
    private final By nextButton = By.xpath(".//div[starts-with(@class, 'Order_NextButton')]/button");

    // поле 'когда привезти самокат'
    private final By dateInput = By.xpath(".//div[starts-with(@class, 'react-datepicker__input-container')]//input");
    //выбранная дата в календаре
    private final By dateSelected = By.className("react-datepicker__day--selected");
    // срок аренды
    private final By termDropdown = By.className("Dropdown-root");
    //элементы списка 'срок аренды'
    private final By termDropdownOption = By.className("Dropdown-option");
    // чекбокс 'цвет самоката'
    private final By colorLabels = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]//label");
    // коммент для курьера
    private final By commentInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Комментарий для курьера')]");
    // кнопка 'заказать'
    private final By orderButton = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[not(contains(@class,'Button_Inverted'))]");
    //кнопка 'да' при подтверждении заказа
    private final By acceptOrderButton = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//button[not(contains(@class,'Button_Inverted'))]");
    //уведомление об успешном заказе
    private final By orderSuccessMessage = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//div[(starts-with(@class,'Order_ModalHeader'))]");

    private final WebDriver webDriver;

    public OrderPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void waitForLoadForm() {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElement(this.orderForm)));
    }

    private void waitForItemLoad(By itemToLoad) {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElement(itemToLoad)));

    }

    public void setName (String name) {
        this.webDriver.findElement(this.nameInput).sendKeys(name);
    }

    public void setSurname(String surname) {
        this.webDriver.findElement(this.surnameInput).sendKeys(surname);
    }

    public void setAddress(String address) {
        this.webDriver.findElement(this.addressInput).sendKeys(address);
    }

    public void setMetro(String metro) {
        this.webDriver.findElement(this.metroInput).sendKeys(metro);
        this.waitForItemLoad(this.metroList);
        this.chooseElementFromDropdown(this.metroListItems, metro);
    }

    public void setPhone(String phone) {
        this.webDriver.findElement(this.phoneInput).sendKeys(phone);
    }

    public void clickNextButton() {
        this.webDriver.findElement(this.nextButton).click();
    }

    public void setDate(String date) {
        this.webDriver.findElement(this.dateInput).sendKeys(date);
        this.waitForItemLoad(this.dateSelected);
        this.clickDateSelected();
    }

    private void clickDateSelected() {
        this.webDriver.findElement(this.dateSelected).click();
    }

    public void setTerm(String termToChoose) {
        this.clickTermDropdown();
        this.chooseElementFromDropdown(this.termDropdownOption, termToChoose);
    }

    public void clickTermDropdown() {
        this.webDriver.findElement(this.termDropdown).click();
    }

    private void chooseElementFromDropdown(By dropdownElements, String elementToChoose) {
        List<WebElement> elementsFiltered = this.webDriver.findElements(dropdownElements);
        for (WebElement element : elementsFiltered) {
            if (element.getText().equals(elementToChoose)) {
                element.click();
                break;
            }
        }
    }

    public void setColor(String colorToChoose) {
        this.chooseElementFromDropdown(this.colorLabels, colorToChoose);
    }

    public void setComment(String comment) {
        this.webDriver.findElement(this.commentInput).sendKeys(comment);
    }

    private void clickOrderButton() {
        this.webDriver.findElement(this.orderButton).click();
    }

    private void clickAcceptOrderButton() {
        this.webDriver.findElement(this.acceptOrderButton).click();
    }

    public String getNewOrderSuccessMessage() {
        return this.webDriver.findElement(this.orderSuccessMessage).getText();
    }

    public void makeOrder() {
        this.clickOrderButton();
        this.waitForItemLoad(this.acceptOrderButton);
        this.clickAcceptOrderButton();
    }
}
