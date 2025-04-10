package utils;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class MyExtentReport implements ITestListener {

	private ExtentSparkReporter spark; // created the UI and layout of the report
	private ExtentReports report; // captures general information into the report
	private ExtentTest test; // create a new entry in the report AND update the report with test
								// status(PASS/FAIL/SKIP)
	static String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html"; // set the report path

	static WebDriver Mydriver; // we need this for screenshot capture

	// Setter for WebDriver instance
	public static void setDriver(WebDriver webDriver) {
	Mydriver = webDriver;
	}

	@Override
	public void onStart(ITestContext context) {
		spark = new ExtentSparkReporter(reportPath);

		report = new ExtentReports();
		// Attach the spark reporter to the report:
		report.attachReporter(spark);

		// call methods of ExtentSparkReporter to set the layout of the report:
		spark.config().setDocumentTitle("Automation Report"); // title of report
		spark.config().setReportName("Functional Test Report"); // name of report
		// spark.config().setTheme(Theme.DARK); // Theme: can be STANDARD also.
		spark.config().setTheme(Theme.DARK);

		// call methods of the ExtentReports class to send general information to the
		// report like:
		report.setSystemInfo("Tester", "DC");
		report.setSystemInfo("Environment", "QA");
		report.setSystemInfo("Browser", "Mozilla");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test = report.createTest(result.getName()); // create a new entry in the report
		test.log(Status.PASS, "****Test case PASSED IS " + result.getName()); // send test status for PASS

	}

	@Override
	public void onTestFailure(ITestResult result) {
		test = report.createTest(result.getName()); // create a new entry in the report
		test.log(Status.FAIL, "****Test case FAILED IS " + result.getName()); // send test status for FAIL
		test.log(Status.FAIL, "****FAILED REASON IS " + result.getThrowable()); // returns the exception

		// call the method for the capture screenshot if driver!=null;

		if (Mydriver != null) {
			String screenshotPath = captureScreenshot(result.getName());
			try {
				test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
			} catch (Exception e) {
				test.log(Status.FAIL, "⚠️ Error attaching screenshot: " + e.getMessage());
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test = report.createTest(result.getName()); // create a new entry in the report
		test.log(Status.SKIP, "****Test case SKIPPED IS " + result.getName()); // send test status for Skips
		test.log(Status.SKIP, "****SKIPPED REASON IS " + result.getThrowable()); // returns the exception

	}

	@Override
	public void onFinish(ITestContext context) {
		report.flush();
		System.out.println("Finished all test cases.");
		if (Mydriver != null) {
			Mydriver.quit();
		}
	}

	// the method to capture the screenshot. To be called from onTestFailure;
	public static String captureScreenshot(String testName) {
		// set the screenshot directory: create a directory under my project path and
		// store the screenshots there
		String screenshotDir = System.getProperty("user.dir") + "/all-screenshots/";

		// Create directory if it doesn't exist
		File directory = new File(screenshotDir);
		if (!directory.exists()) {
			directory.mkdirs();
			System.out.println("📁 Created directory: " + screenshotDir);
		}
		// Define screenshot file path
		String screenshotPath = screenshotDir + testName + ".png"; // set the file name under the directory

		// for full page screenshot
		// 1. Typecast the webdriver with (TakesScreenshot)object
		TakesScreenshot ts = (TakesScreenshot) Mydriver;

		// 2.We set the screenshot file type
		// getScreenshotAs -->returns an object of the File Class
		File srcFile = ts.getScreenshotAs(OutputType.FILE);

		// 3. create our target file. This is the actual file that we are going to use.
		// We set the screenshot path here.
		File destFile = new File(screenshotPath);

		// 4. copy the source file to this targetfile:
		try {
			srcFile.renameTo(destFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// returns the screenshot path to the onTestFailure() method
		System.out.println("Screenshot is saved in location --->" + screenshotPath);
		return (screenshotPath);

	}// end of capture screenshot method

}
