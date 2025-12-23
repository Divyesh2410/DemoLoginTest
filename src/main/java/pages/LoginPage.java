package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.WaitHelper;

public class LoginPage {

	private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
	private WebDriver driver;
	private WaitHelper waitHelper;

	// locators
	private By username = By.id("username");
	private By password = By.id("password");
	private By loginBtn = By.cssSelector("button[type='submit']");
	private By successMsg = By.id("flash");
	private By failMsg = By.id("flash");

	/**
	 * Constructor to initialize LoginPage with WebDriver instance
	 */
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.waitHelper = new WaitHelper(driver);
		logger.info("LoginPage initialized");
	}

	/**
	 * Enters username into the username field
	 */
	public void enterusername(String user) {
		logger.info("Entering username: {}", user);
		WebElement usernameElement = waitHelper.waitForElementToBeVisible(username);
		usernameElement.clear();
		usernameElement.sendKeys(user);
		logger.debug("Username entered successfully");
	}

	/**
	 * Enters password into the password field
	 */
	public void enterpassword(String pass) {
		logger.info("Entering password");
		WebElement passwordElement = waitHelper.waitForElementToBeVisible(password);
		passwordElement.clear();
		passwordElement.sendKeys(pass);
		logger.debug("Password entered successfully");
	}

	/**
	 * Clicks the login button to submit the form
	 */
	public void clickloginbtn() {
		logger.info("Clicking on login button");
		WebElement loginButton = waitHelper.waitForElementToBeClickable(loginBtn);
		loginButton.click();
		logger.info("Login button clicked");
	}

	/**
	 * Retrieves the success message displayed after successful login
	 */
	public String getSuccessmessage() {
		logger.info("Retrieving success message");
		WebElement messageElement = waitHelper.waitForElementToBeVisible(successMsg);
		String message = messageElement.getText();
		logger.info("Success message retrieved: {}", message);
		return message;
	}
	
	/**
	 * Retrieves the failure message displayed after failed login attempt
	 */
	public String getFailMessage() {
		logger.info("Retrieving failure message");
		WebElement messageElement = waitHelper.waitForElementToBeVisible(failMsg);
		String message = messageElement.getText();
		logger.info("Failure message retrieved: {}", message);
		return message;
	}

	/**
	 * Retrieves any flash message (success or failure) from the page
	 */
	public String getFlashMessage() {
		logger.info("Retrieving flash message");
		WebElement messageElement = waitHelper.waitForElementToBeVisible(successMsg);
		String message = messageElement.getText();
		logger.info("Flash message retrieved: {}", message);
		return message;
	}

	/**
	 * Returns the current page URL
	 */
	public String getCurrentUrl() {
		String currentUrl = driver.getCurrentUrl();
		logger.info("Current URL: {}", currentUrl);
		return currentUrl;
	}

	/**
	 * Checks if the current page is the login page
	 */
	public boolean isOnLoginPage() {
		String currentUrl = getCurrentUrl();
		boolean isLoginPage = currentUrl.contains("/login");
		logger.info("Is on login page: {}", isLoginPage);
		return isLoginPage;
	}

	/**
	 * Checks if the current page is the secure page (after successful login)
	 */
	public boolean isOnSecurePage() {
		String currentUrl = getCurrentUrl();
		boolean isSecurePage = currentUrl.contains("/secure");
		logger.info("Is on secure page: {}", isSecurePage);
		return isSecurePage;
	}
}
