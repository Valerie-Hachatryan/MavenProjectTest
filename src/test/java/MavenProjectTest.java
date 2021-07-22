import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MavenProjectTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(MavenProjectTest.class);

    // Читаем передаваемый параметр browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");
    // Читаем передаваемый параметр option (-Doption)
    String load = System.getProperty("option", "normal");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverFactory.getDriver(env.toLowerCase());
        logger.info("Драйвер стартовал!");
        driver = WebDriverFactory.getOption(load.toLowerCase());
        logger.info("Page Load strategy is: " + load);
    }

    @Test
    public void openPage() {

        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        //Открываем окно в полноэкранном режиме
        driver.manage().window().fullscreen();
        //Отображение размеров окна браузера
        logger.info(String.format("Browser Window Height: %d", driver.manage().window().getSize().getHeight()));
        logger.info(String.format("Browser Window Width: %d", driver.manage().window().getSize().getWidth()));

        //Выводим заголовок страницы
        String title = driver.getTitle();
        logger.info("title - " + title.toString());

        //Выводим текущий URL
        String currentURL = driver.getCurrentUrl();
        logger.info("current URL" + currentURL.toString());

        //Добавляем ожидание в 30 секунд
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        //Закрыть всплывашку
        WebElement cityAccept = driver.findElement(By.xpath("//a[contains(@rel, 'nofollow noopener')]"));
        cityAccept.click();

        // Нажать на ссылку Бытовая техника
        WebElement linkAppliances = driver.findElement(By.xpath("//a[text()=\"Бытовая техника\"]"));
        linkAppliances.click();
        logger.info("Нажата ссылка Бытовая техника");

        //Вывод доступных категорий в лог
        String query = "//*[@class=\"subcategory\"]";
        // Поиск родительского веб-элемента
        WebElement parentElement = driver.findElement(By.xpath(query));
        String query2 = "//*[@class=\"subcategory__item ui-link ui-link_blue\"]";
        List<WebElement> childElements  = parentElement.findElements(By.xpath(query2));
        for (WebElement childElement : childElements) {
            logger.info("Категория: " + childElement.getTagName() + " = " + childElement.getText());
        }

        // Создание куки Cookie 1 и вывод информации по нему
        logger.info("Куки, которое добавили мы");
        driver.manage().addCookie(new Cookie("Cookie 1", "This Is Cookie 1"));
        Cookie cookie1 = driver.manage().getCookieNamed("Cookie 1");
        logger.info(String.format("Domain: %s", cookie1.getDomain()));
        logger.info(String.format("Expiry: %s", cookie1.getExpiry()));
        logger.info(String.format("Name: %s", cookie1.getName()));
        logger.info(String.format("Path: %s", cookie1.getPath()));
        logger.info(String.format("Value: %s", cookie1.getValue()));
        logger.info("--------------------------------------");

        // Вывод информации по кукам DNS-shop.ru
        logger.info("Куки, которое добавил DNS");
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies) {
            logger.info(String.format("Domain: %s", cookie.getDomain()));
            logger.info(String.format("Expiry: %s", cookie.getExpiry()));
            logger.info(String.format("Name: %s", cookie.getName()));
            logger.info(String.format("Path: %s", cookie.getPath()));
            logger.info(String.format("Value: %s", cookie.getValue()));
            logger.info("--------------------------------------");
        }

        // Добавляем задержку sleep чтобы увидеть результат
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
             e.printStackTrace();
        }*/
    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
}