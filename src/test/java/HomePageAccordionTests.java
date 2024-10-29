import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageObjects.HomePage;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class HomePageAccordionTests {
    private WebDriver webDriver;

    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru/";

    private final int numberOfAccordionItem;
    private final String expectedHeaderText;
    private final String expectedItemText;

    public HomePageAccordionTests(int numberOfAccordeonItem, String expectedHeaderText, String expectedItemText) {
        this.numberOfAccordionItem = numberOfAccordeonItem;
        this.expectedHeaderText = expectedHeaderText;
        this.expectedItemText = expectedItemText;
    }

    //ошибка в тексте 8 элемента - "Я жиЗу", а должно быть "Я жиВу"
    @Parameterized.Parameters
    public static Object[][] setTestData() {
        return new Object[][] {
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области." }, // bug here з -> в
        };
    }

    @Before
    public void startUp() {
        //WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        //this.webDriver = new ChromeDriver();
        this.webDriver = new FirefoxDriver();
        this.webDriver.get(this.mainPageUrl);
    }

    @Test
    public void checkAccuracyOfAccordionText(){
        HomePage homePage = new HomePage(this.webDriver);

        homePage.clickAcceptCookieButton();

        homePage.scrollIntoAccordionHeader();

        homePage.clickAccordionHeader(this.numberOfAccordionItem);
        homePage.waitForLoadItem(this.numberOfAccordionItem);

        if (homePage.isAccordionItemDisplayed(this.numberOfAccordionItem)) {
            MatcherAssert.assertThat("problems with accordion header #" + this.numberOfAccordionItem,
                    this.expectedHeaderText,
                    equalTo(homePage.getAccordionHeaderText(this.numberOfAccordionItem)));
        }
    }

    @After
    public void tearDown() {
        this.webDriver.quit();
    }
}
