package base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

public class BaseClass {

	private static final Logger logger = LoggerFactory.getLogger(BaseClass.class);
	protected WebDriver driver;
	protected Properties prop;

	@BeforeMethod
	public void setup() {
		logger.info("===== Starting test execution =====");
		prop = ConfigReader.loadconfig();
		logger.info("Configuration loaded");
		
		// Get headless mode from configuration (can be overridden by Jenkins parameter)
		String headlessMode = prop.getProperty("headless", "false");
		boolean isHeadless = Boolean.parseBoolean(headlessMode);
		
		logger.info("Setting up ChromeDriver");
		WebDriverManager.chromedriver().setup();
		
		// Configure ChromeOptions based on headless setting
		ChromeOptions options = new ChromeOptions();
		if (isHeadless) {
			logger.info("Running in HEADLESS mode (browser will not be visible)");
			options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--window-size=1920,1080");
		} else {
			logger.info("Running in VISIBLE mode (browser will be visible)");
		}
		
		driver = new ChromeDriver(options);
		logger.info("ChromeDriver initialized");

		if (!isHeadless) {
			logger.info("Maximizing browser window");
			driver.manage().window().maximize();
		}
		
		String url = prop.getProperty("url");
		logger.info("Navigating to URL: {}", url);
		driver.get(url);
		logger.info("Page loaded successfully");
	}

	@AfterMethod
	public void quit() {
		if (driver != null) {
			logger.info("Closing browser");
			driver.quit();
			logger.info("Browser closed successfully");
		}
		logger.info("===== Test execution completed =====");
	}
}
