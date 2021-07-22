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
    private static String browser;


    public static WebDriver getDriver(String browserName) {
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                //Специфические настройки браузера
                browser = "Chrome";
                ChromeOptions options = new ChromeOptions();
                options.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                options.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
                // Добавление аргументов запуска Google Chrome
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");

                logger.info("Драйвер для браузера Google Chrome");
                return new ChromeDriver(options);
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                browser = "Firefox";
                FirefoxOptions optionsf = new FirefoxOptions();
                optionsf.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                optionsf.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                optionsf.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
                //
                optionsf.addArguments("--start-maximized");
                optionsf.addArguments("--private");

                logger.info("Драйвер для браузера Mozilla Firefox");
                return new FirefoxDriver(optionsf);
            default:
                throw new RuntimeException("Incorrect browser name");
        }
    }
    
    public static WebDriver getOption(String optionType) {
        if (browser.equals("Firefox")) {
            switch (optionType) {
                case "eager":
                    FirefoxOptions options = new FirefoxOptions();
                    options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    logger.info("Page Load Strategy is eager");
                    return new FirefoxDriver(options);
                case "none":
                    options = new FirefoxOptions();
                    options.setPageLoadStrategy(PageLoadStrategy.NONE);
                    logger.info("Page Load Strategy is none");
                    return new FirefoxDriver(options);
                default:
                    options = new FirefoxOptions();
                    options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    logger.info("Page Load Strategy is normal");
                    return new FirefoxDriver(options);
            }  } else {
            switch (optionType) {
                case "eager":
                    ChromeOptions options = new ChromeOptions();
                    options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    logger.info("Page Load Strategy is eager");
                    return new ChromeDriver(options);
                case "none":
                    options = new ChromeOptions();
                    options.setPageLoadStrategy(PageLoadStrategy.NONE);
                    logger.info("Page Load Strategy is none");
                    return new ChromeDriver(options);
                default:
                    options = new ChromeOptions();
                    options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    logger.info("Page Load Strategy is normal");
                    return new ChromeDriver(options);
            }
        }
    }
}

