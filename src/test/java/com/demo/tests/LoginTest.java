package com.demo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.BaseClass;
import pages.LoginPage;

public class LoginTest extends BaseClass {

	private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);
	private static final String VALID_USERNAME = "tomsmith";
	private static final String VALID_PASSWORD = "SuperSecretPassword!";

	// ==================== POSITIVE TEST CASES ====================

	@Test(priority = 1, description = "Verify successful login with valid credentials")
	public void testSuccessfulLoginWithValidCredentials() {
		logger.info("=== POSITIVE TEST: Successful login with valid credentials ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with valid credentials");
		loginPage.enterusername(prop.getProperty("username"));
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying success message");
		String message = loginPage.getSuccessmessage();
		Assert.assertTrue(message.contains("You logged into a secure area"),
				"Expected success message not found. Actual message: " + message);
		
		logger.info("Verifying navigation to secure page");
		Assert.assertTrue(loginPage.isOnSecurePage(), "User should be redirected to secure page after successful login");
		logger.info("Test PASSED: Successfully logged into secure area");
	}

	@Test(priority = 2, description = "Verify successful login with hardcoded valid credentials")
	public void testSuccessfulLoginWithHardcodedCredentials() {
		logger.info("=== POSITIVE TEST: Successful login with hardcoded valid credentials ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with hardcoded valid credentials");
		loginPage.enterusername(VALID_USERNAME);
		loginPage.enterpassword(VALID_PASSWORD);
		loginPage.clickloginbtn();

		logger.info("Verifying success message");
		String message = loginPage.getSuccessmessage();
		Assert.assertTrue(message.contains("You logged into a secure area"),
				"Expected success message not found. Actual message: " + message);
		
		logger.info("Verifying URL contains /secure");
		Assert.assertTrue(loginPage.getCurrentUrl().contains("/secure"),
				"URL should contain /secure after successful login");
		logger.info("Test PASSED: Login successful with hardcoded credentials");
	}

	// ==================== NEGATIVE TEST CASES ====================

	@Test(priority = 3, description = "Verify login fails with invalid username")
	public void testLoginFailureWithInvalidUsername() {
		logger.info("=== NEGATIVE TEST: Login failure with invalid username ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with invalid username");
		loginPage.enterusername("invaliduser");
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFailMessage();
		Assert.assertTrue(message.contains("Your username is invalid!"),
				"Expected failure message not found. Actual message: " + message);
		
		logger.info("Verifying user remains on login page");
		Assert.assertTrue(loginPage.isOnLoginPage(), "User should remain on login page after failed login");
		logger.info("Test PASSED: Login failure handled correctly for invalid username");
	}

	@Test(priority = 4, description = "Verify login fails with invalid password")
	public void testLoginFailureWithInvalidPassword() {
		logger.info("=== NEGATIVE TEST: Login failure with invalid password ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with invalid password");
		loginPage.enterusername(prop.getProperty("username"));
		loginPage.enterpassword("WrongPassword123");
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFailMessage();
		Assert.assertTrue(message.contains("Your password is invalid!"),
				"Expected failure message not found. Actual message: " + message);
		
		logger.info("Verifying user remains on login page");
		Assert.assertTrue(loginPage.isOnLoginPage(), "User should remain on login page after failed login");
		logger.info("Test PASSED: Login failure handled correctly for invalid password");
	}

	@Test(priority = 5, description = "Verify login fails with both invalid username and password")
	public void testLoginFailureWithBothInvalidCredentials() {
		logger.info("=== NEGATIVE TEST: Login failure with both invalid credentials ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with both invalid credentials");
		loginPage.enterusername("wronguser");
		loginPage.enterpassword("wrongpass");
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFailMessage();
		Assert.assertTrue(message.contains("Your username is invalid!") || message.contains("Your password is invalid!"),
				"Expected failure message not found. Actual message: " + message);
		
		logger.info("Verifying user remains on login page");
		Assert.assertTrue(loginPage.isOnLoginPage(), "User should remain on login page after failed login");
		logger.info("Test PASSED: Login failure handled correctly for invalid credentials");
	}

	@Test(priority = 6, description = "Verify login fails with empty username")
	public void testLoginFailureWithEmptyUsername() {
		logger.info("=== NEGATIVE TEST: Login failure with empty username ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with empty username");
		loginPage.enterusername("");
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying failure message or validation");
		String message = loginPage.getFlashMessage();
		logger.info("Message received: {}", message);
		// The page might show validation error or invalid username message
		Assert.assertTrue(message.contains("Your username is invalid!") || message.trim().isEmpty(),
				"Expected validation message not found. Actual message: " + message);
		
		logger.info("Verifying user remains on login page");
		Assert.assertTrue(loginPage.isOnLoginPage(), "User should remain on login page");
		logger.info("Test PASSED: Empty username validation handled correctly");
	}

	@Test(priority = 7, description = "Verify login fails with empty password")
	public void testLoginFailureWithEmptyPassword() {
		logger.info("=== NEGATIVE TEST: Login failure with empty password ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with empty password");
		loginPage.enterusername(prop.getProperty("username"));
		loginPage.enterpassword("");
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		logger.info("Message received: {}", message);
		// The page might show validation error or invalid password message
		Assert.assertTrue(message.contains("Your password is invalid!") || message.trim().isEmpty(),
				"Expected validation message not found. Actual message: " + message);
		
		logger.info("Verifying user remains on login page");
		Assert.assertTrue(loginPage.isOnLoginPage(), "User should remain on login page");
		logger.info("Test PASSED: Empty password validation handled correctly");
	}

	@Test(priority = 8, description = "Verify login fails with both fields empty")
	public void testLoginFailureWithBothFieldsEmpty() {
		logger.info("=== NEGATIVE TEST: Login failure with both fields empty ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with both fields empty");
		loginPage.enterusername("");
		loginPage.enterpassword("");
		loginPage.clickloginbtn();

		logger.info("Verifying user remains on login page");
		Assert.assertTrue(loginPage.isOnLoginPage(), "User should remain on login page");
		logger.info("Test PASSED: Empty fields validation handled correctly");
	}

	@Test(priority = 9, description = "Verify login fails with username containing only spaces")
	public void testLoginFailureWithUsernameContainingOnlySpaces() {
		logger.info("=== NEGATIVE TEST: Login failure with username containing only spaces ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with username containing only spaces");
		loginPage.enterusername("   ");
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		Assert.assertTrue(message.contains("Your username is invalid!") || message.trim().isEmpty(),
				"Expected failure message not found. Actual message: " + message);
		
		logger.info("Verifying user remains on login page");
		Assert.assertTrue(loginPage.isOnLoginPage(), "User should remain on login page");
		logger.info("Test PASSED: Whitespace-only username handled correctly");
	}

	@Test(priority = 10, description = "Verify login fails with password containing only spaces")
	public void testLoginFailureWithPasswordContainingOnlySpaces() {
		logger.info("=== NEGATIVE TEST: Login failure with password containing only spaces ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with password containing only spaces");
		loginPage.enterusername(prop.getProperty("username"));
		loginPage.enterpassword("   ");
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		Assert.assertTrue(message.contains("Your password is invalid!") || message.trim().isEmpty(),
				"Expected failure message not found. Actual message: " + message);
		
		logger.info("Test PASSED: Whitespace-only password handled correctly");
	}

	@Test(priority = 11, description = "Verify login fails with SQL injection attempt in username")
	public void testLoginFailureWithSQLInjectionInUsername() {
		logger.info("=== NEGATIVE TEST: Login failure with SQL injection attempt in username ===");
		LoginPage loginPage = new LoginPage(driver);

		String sqlInjection = "admin' OR '1'='1";
		logger.info("Executing login with SQL injection attempt: {}", sqlInjection);
		loginPage.enterusername(sqlInjection);
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		Assert.assertTrue(message.contains("Your username is invalid!"),
				"SQL injection should be rejected. Actual message: " + message);
		
		logger.info("Verifying user remains on login page");
		Assert.assertTrue(loginPage.isOnLoginPage(), "User should remain on login page");
		logger.info("Test PASSED: SQL injection attempt blocked correctly");
	}

	@Test(priority = 12, description = "Verify login fails with XSS attempt in username")
	public void testLoginFailureWithXSSAttemptInUsername() {
		logger.info("=== NEGATIVE TEST: Login failure with XSS attempt in username ===");
		LoginPage loginPage = new LoginPage(driver);

		String xssAttempt = "<script>alert('XSS')</script>";
		logger.info("Executing login with XSS attempt: {}", xssAttempt);
		loginPage.enterusername(xssAttempt);
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		Assert.assertTrue(message.contains("Your username is invalid!"),
				"XSS attempt should be rejected. Actual message: " + message);
		
		logger.info("Test PASSED: XSS attempt blocked correctly");
	}

	@Test(priority = 13, description = "Verify login fails with special characters in username")
	public void testLoginFailureWithSpecialCharactersInUsername() {
		logger.info("=== NEGATIVE TEST: Login failure with special characters in username ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with special characters in username");
		loginPage.enterusername("user@#$%^&*()");
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		Assert.assertTrue(message.contains("Your username is invalid!"),
				"Expected failure message not found. Actual message: " + message);
		
		logger.info("Test PASSED: Special characters in username handled correctly");
	}

	@Test(priority = 14, description = "Verify login fails with very long username")
	public void testLoginFailureWithVeryLongUsername() {
		logger.info("=== NEGATIVE TEST: Login failure with very long username ===");
		LoginPage loginPage = new LoginPage(driver);

		String longUsername = "a".repeat(100);
		logger.info("Executing login with very long username (100 characters)");
		loginPage.enterusername(longUsername);
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		Assert.assertTrue(message.contains("Your username is invalid!"),
				"Expected failure message not found. Actual message: " + message);
		
		logger.info("Test PASSED: Very long username handled correctly");
	}

	@Test(priority = 15, description = "Verify login fails with case-sensitive username mismatch")
	public void testLoginFailureWithCaseSensitiveUsername() {
		logger.info("=== NEGATIVE TEST: Login failure with case-sensitive username mismatch ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with uppercase username");
		loginPage.enterusername("TOMSMITH"); // Username is case-sensitive
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		Assert.assertTrue(message.contains("Your username is invalid!"),
				"Case-sensitive username should be rejected. Actual message: " + message);
		
		logger.info("Test PASSED: Case-sensitive username validation handled correctly");
	}

	@Test(priority = 16, description = "Verify login fails with incorrect password case")
	public void testLoginFailureWithIncorrectPasswordCase() {
		logger.info("=== NEGATIVE TEST: Login failure with incorrect password case ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with lowercase password");
		loginPage.enterusername(prop.getProperty("username"));
		loginPage.enterpassword("supersecretpassword!"); // Wrong case
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		Assert.assertTrue(message.contains("Your password is invalid!"),
				"Password case sensitivity should be enforced. Actual message: " + message);
		
		logger.info("Test PASSED: Password case sensitivity handled correctly");
	}

	@Test(priority = 17, description = "Verify login fails with username that is close to valid but incorrect")
	public void testLoginFailureWithSimilarButIncorrectUsername() {
		logger.info("=== NEGATIVE TEST: Login failure with similar but incorrect username ===");
		LoginPage loginPage = new LoginPage(driver);

		logger.info("Executing login with username similar to valid one");
		loginPage.enterusername("tomsmith_extra");
		loginPage.enterpassword(prop.getProperty("password"));
		loginPage.clickloginbtn();

		logger.info("Verifying failure message");
		String message = loginPage.getFlashMessage();
		Assert.assertTrue(message.contains("Your username is invalid!"),
				"Expected failure message not found. Actual message: " + message);
		
		logger.info("Test PASSED: Similar but incorrect username rejected correctly");
	}
}
