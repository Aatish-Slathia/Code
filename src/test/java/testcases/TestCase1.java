package testcases;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestCase1 {

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeTest
	public void setReport() {

		htmlReporter = new ExtentHtmlReporter("./reports/Reports.html");

		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("Automation Reports");
		htmlReporter.config().setReportName("Automation Test Results");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("Automation Tester", "Rakesh Kumar");
		extent.setSystemInfo("Orgainzation", "AutomationInc");
		extent.setSystemInfo("Build No", "AI-1234");

	}

	@AfterTest
	public void endReport() {

		extent.flush();
	}

	/*
	 * Pass, Fail, Skip
	 * 
	 */

	@Test(priority =1)
	public void doLogin() {

		test = extent.createTest("Login Test");
		System.out.println("Executing Login Test");
	}

	@Test(priority =2)
	public void doUserReg() {

		test = extent.createTest("User Reg Test");
		Assert.fail("User Reg Test Failed");
	}

	@Test(priority =3)
	public void isSkip() {

		test = extent.createTest("Skip Test");
		throw new SkipException("Skipping the test case");
	}
	
	@Test(priority =4)
	public void doLogOut() {

		test = extent.createTest("Logout Test");
		System.out.println("Executing Logout Test");
	}

	@Test(priority =5)
	public void doCleanUp() {

		test = extent.createTest("CleanUp Test");
		Assert.fail("User Reg Test Failed");
	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			
			
			String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());
			test.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
					+ "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>"
					+ " \n");
			
		/*	
			try {

				ExtentManager.captureScreenshot();
				testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
						MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName)
								.build());
			} catch (IOException e) {

			}*/
			
			

			String failureLogg = "TEST CASE FAILED";
			Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
			test.log(Status.FAIL, m);
			
			

		} else if (result.getStatus() == ITestResult.SKIP) {

			String methodName = result.getMethod().getMethodName();

			String logText = "<b>" + "TEST CASE: - " + methodName.toUpperCase() + "  SKIPPED" + "</b>";

			Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
			test.skip(m);

		} else if (result.getStatus() == ITestResult.SUCCESS) {

			String methodName = result.getMethod().getMethodName();

			String logText = "<b>" + "TEST CASE: - " + methodName.toUpperCase() + "  PASSED" + "</b>";

			Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
			test.pass(m);

		}

	}

}
