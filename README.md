# Login Automation Test Framework

A Selenium-based test automation framework for login functionality testing using Page Object Model (POM) design pattern.

## 🚀 Quick Start

```bash
# Clone the repository
git clone https://github.com/Divyesh2410/DemoLoginTest.git
cd LoginTestDemo

# Build and run tests
mvn clean test

# View reports
# ExtentReports: reports/ExtentReport.html
# TestNG Reports: test-output/index.html
```

## 📋 Prerequisites

- Java JDK 11 or higher
- Maven 3.6+
- Chrome Browser (latest version)

## 🏗️ Project Structure

```
src/
├── main/java/
│   ├── base/BaseClass.java           # Base test class with setup/teardown
│   ├── pages/LoginPage.java          # Login page object model
│   ├── utils/
│   │   ├── ConfigReader.java         # Configuration reader
│   │   └── WaitHelper.java           # Explicit wait utility
│   └── reports/ExtentManager.java    # ExtentReports manager
└── test/java/com/demo/
    ├── framework/listeners/TestListener.java  # TestNG listener
    └── tests/LoginTest.java          # Test cases (17 test scenarios)
```

## ⚙️ Configuration

Edit `src/test/resources/config.properties`:

```properties
url=https://the-internet.herokuapp.com/login
browser=chrome
username=tomsmith
password=SuperSecretPassword!
```

## ▶️ Running Tests

**From IDE:**
- Right-click `testng.xml` → Run As → TestNG Suite
- Right-click `LoginTest.java` → Run As → TestNG Test

**From Command Line:**
```bash
mvn clean test                          # Run all tests
mvn test -Dtest=LoginTest              # Run specific class
mvn test -Dtest=LoginTest#testMethod   # Run specific method
```

## 🧪 Test Coverage

**17 Test Cases:**
- ✅ 2 Positive tests (valid login scenarios)
- ❌ 15 Negative tests (invalid inputs, security, edge cases)

Tests cover:
- Valid/invalid credentials
- Empty fields validation
- SQL injection & XSS attacks
- Special characters & boundary testing
- Case sensitivity checks

## 📊 Reports

- **ExtentReports:** `reports/ExtentReport.html` - Beautiful HTML report with detailed test results
- **TestNG Reports:** `test-output/index.html` - Standard TestNG HTML report

## 🛠️ Technology Stack

- **Java 11** - Programming language
- **Selenium WebDriver 4.21.0** - Web automation
- **TestNG 7.10.2** - Test framework
- **Maven** - Build tool
- **ExtentReports 5.1.1** - Test reporting
- **SLF4J 2.0.9** - Logging

## ✨ Features

- ✅ Page Object Model (POM) pattern
- ✅ Explicit waits (no Thread.sleep)
- ✅ Comprehensive logging
- ✅ HTML test reports
- ✅ 17 comprehensive test scenarios

## 📝 Logging

Framework uses SLF4J for detailed logging. Logs appear in console during test execution showing:
- Test execution flow
- Element interactions
- Assertions and results
- Execution time

## 👤 Author

**Divyesh**
