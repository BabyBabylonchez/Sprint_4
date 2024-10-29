import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageObjects.HomePage;
import pageObjects.OrderPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(Parameterized.class)
public class OrderPageTests {
    private WebDriver webDriver;

    private final String homePageUrl = "https://qa-scooter.praktikum-services.ru";

    private final String name, surname, address, metro, phone, date, term, color, comment;

    private final String expectedOrderSuccessText = "Заказ оформлен";

    public OrderPageTests(
            String name,
            String surname,
            String address,
            String metro,
            String phone,
            String date,
            String term,
            String color,
            String comment
    ) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.term = term;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters()
    public static Object[][] setDataForOrder() {
        return new Object[][] {
                {"Сергей", "Иванов", "Москва, ул. Правобережная, д. 1, кв. 1", "Октябрьская", "+79999999999", "01.01.2024", "четверо суток", "чёрный жемчуг", "Коммент"},
                {"Иван ", "Петров", "Москва, ул. Левобережная, д. 2, кв. 2", "Третьяковская", "+78888888888", "02.02.2024", "трое суток", "серая безысходность", "Коммент"},
        };
    }

    @Before
    public void startUp() {
        //WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        //this.webDriver = new ChromeDriver();    // здесь тест падает
        this.webDriver = new FirefoxDriver();
        this.webDriver.get(homePageUrl);
    }

    @Test
    public void orderWithHeaderButtonWhenSuccess() {
        HomePage homePage = new HomePage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        homePage.clickAcceptCookieButton();
        homePage.clickOrderButtonHeader();
        makeOrder(orderPage);

        MatcherAssert.assertThat(
                "Problem with creating a new order",
                orderPage.getNewOrderSuccessMessage(),
                containsString(this.expectedOrderSuccessText)
        );
    }

    @Test
    public void orderWithBodyButtonWhenSuccess() {
        HomePage homePage = new HomePage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        homePage.clickAcceptCookieButton();
        homePage.clickOrderButtonBody();
        makeOrder(orderPage);

        MatcherAssert.assertThat(
                "Problem with creating a new order",
                orderPage.getNewOrderSuccessMessage(),
                containsString(this.expectedOrderSuccessText)
        );
    }

    private void makeOrder(OrderPage orderPage) {
        orderPage.waitForLoadForm();

        orderPage.setName(this.name);
        orderPage.setSurname(this.surname);
        orderPage.setAddress(this.address);
        orderPage.setMetro(this.metro);
        orderPage.setPhone(this.phone);

        orderPage.clickNextButton();

        orderPage.setDate(this.date);
        orderPage.setTerm(this.term);
        orderPage.setColor(this.color);
        orderPage.setComment(this.comment);

        orderPage.makeOrder();
    }

    @After
    public void tearDown() {
        this.webDriver.quit();
    }
}
