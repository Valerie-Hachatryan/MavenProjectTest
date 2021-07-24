import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;


public class WebDriverFactory {
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);



    public static WebDriver getDriver(String browserName, String optionName) {
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                //Специфические настройки браузера
                ChromeOptions options = new ChromeOptions();
                options.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                options.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
                // Добавление аргументов запуска Google Chrome
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");

                logger.info("Драйвер для браузера Google Chrome");
                if (optionName.equals("none")) {
                    options.setPageLoadStrategy(PageLoadStrategy.NONE);
                    logger.info("FactoryPage Load Strategy is none");
                } else if (optionName.equals("eager")){
                    options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    logger.info("Page Load Strategy is eager");
                } else {
                    options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    logger.info("Page Load Strategy is normal");
                }
                return new ChromeDriver(options);
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions optionsf = new FirefoxOptions();
                optionsf.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                optionsf.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                optionsf.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
                //
                optionsf.addArguments("--start-maximized");
                optionsf.addArguments("--private");

                logger.info("Драйвер для браузера Mozilla Firefox");
                if (optionName.equals("none")) {
                    optionsf.setPageLoadStrategy(PageLoadStrategy.NONE);
                    logger.info("Page Load Strategy is none");
                } else if (optionName.equals("eager")){
                    optionsf.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    logger.info("Page Load Strategy is eager");
                } else {
                    optionsf.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    logger.info("Page Load Strategy is normal");
                }
                return new FirefoxDriver(optionsf);
            default:
                throw new RuntimeException("Incorrect browser name");
                }

    }
}