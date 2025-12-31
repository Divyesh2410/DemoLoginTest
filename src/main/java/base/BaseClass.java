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
		
		// Get headless mode from configuration (can be overridden by Jenkins parameter)
		String headlessMode = prop.getProperty("headless", "false");
		
		// Parse boolean - explicitly check for "true" (case-insensitive), default to false
		String headlessTrimmed = headlessMode != null ? headlessMode.trim().toLowerCase() : "false";
		boolean isHeadless = "true".equals(headlessTrimmed);
		
		logger.info("Setting up ChromeDriver");
		WebDriverManager.chromedriver().setup();
		
		// Configure ChromeOptions based on headless setting
		ChromeOptions options = new ChromeOptions();
		if (isHeadless) {
			logger.info("Running in HEADLESS mode");
			options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--window-size=1920,1080");
		}
		
		driver = new ChromeDriver(options);

		if (!isHeadless) {
			driver.manage().window().maximize();
		}
		
		String url = prop.getProperty("url");
		driver.get(url);
	}

	@AfterMethod
	public void quit() {
		if (driver != null) {
			driver.quit();
		}
	}
}
