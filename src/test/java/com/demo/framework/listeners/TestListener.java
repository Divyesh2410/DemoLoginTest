package com.demo.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reports.ExtentManager;

public class TestListener implements ITestListener {

	private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
	private static ExtentReports extent = ExtentManager.getExtentReport();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String testClass = result.getTestClass().getName();
		logger.info("Test started: {} in class {}", testName, testClass);
		
		ExtentTest extentTest = extent.createTest(testName);
		extentTest.assignCategory(testClass);
		test.set(extentTest);
		logger.debug("ExtentTest created for: {}", testName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		long duration = result.getEndMillis() - result.getStartMillis();
		logger.info("Test PASSED: {} (Duration: {} ms)", testName, duration);
		
		test.get().log(Status.PASS, "Test Passed");
		test.get().log(Status.PASS, "Execution time: " + duration + " ms");
		logger.debug("ExtentReport updated with PASS status for: {}", testName);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		Throwable throwable = result.getThrowable();
		logger.error("Test FAILED: {}", testName, throwable);
		
		test.get().log(Status.FAIL, "Test Failed");
		test.get().log(Status.FAIL, throwable);
		if (throwable != null) {
			logger.error("Failure reason: {}", throwable.getMessage());
			test.get().fail(throwable);
		}
		logger.debug("ExtentReport updated with FAIL status for: {}", testName);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String skipReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown reason";
		logger.warn("Test SKIPPED: {} - Reason: {}", testName, skipReason);
		
		test.get().log(Status.SKIP, "Test Skipped");
		test.get().log(Status.SKIP, "Skip reason: " + skipReason);
		logger.debug("ExtentReport updated with SKIP status for: {}", testName);
	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("===== Test Suite Execution Completed =====");
		logger.info("Total tests: {}", context.getAllTestMethods().length);
		logger.info("Passed: {}", context.getPassedTests().size());
		logger.info("Failed: {}", context.getFailedTests().size());
		logger.info("Skipped: {}", context.getSkippedTests().size());
		
		extent.flush();
		logger.info("ExtentReport flushed and saved");
	}

	@Override
	public void onStart(ITestContext context) {
		logger.info("===== Test Suite Execution Started =====");
		logger.info("Suite name: {}", context.getSuite().getName());
		logger.info("Test name: {}", context.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		logger.warn("Test failed but within success percentage: {}", testName);
		test.get().warning("Test failed but within success percentage");
	}
}
