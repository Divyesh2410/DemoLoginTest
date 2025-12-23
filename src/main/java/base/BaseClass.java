package base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
		
		logger.info("Setting up ChromeDriver");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		logger.info("ChromeDriver initialized");

		logger.info("Maximizing browser window");
		driver.manage().window().maximize();
		
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
