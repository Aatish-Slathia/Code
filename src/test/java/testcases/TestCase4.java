package testcases;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestCase4 {

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeTest
	public void setReport() {

		htmlReporter = new ExtentHtmlReporter("./reports/extent.html");
		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "./src\\test\\resources\\extent-config.xml");
		
		
//	   htmlReporter.config().setEncoding("utf-8");
//	   htmlReporter.config().setDocumentTitle("Pearson Workflow Automation Reports");
//	   htmlReporter.config().
//	   setReportName("<![CDATA[<img src=\"https://www.logolynx.com/images/logolynx/9f/9f31523b70a72807a19e775bb08ba8e0.png\"/>]]>");
//		  
//		  htmlReporter.config().setTheme(Theme.DARK); 
		  //<b style="background-color: #00af00;">Workflow Automation Report // </b>
		 
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		

	}

	@AfterTest
	public void endReport() {

		extent.flush();
	}

	/*
	 * Pass, Fail, Skip
	 * 
	 */

	@Test(priority = 0)
	public void Login() {

		test = extent.createTest("Login");
		test.log(Status.INFO, "Lauched Browser");
		test.log(Status.INFO, "Entered login credentials");
		test.log(Status.INFO, "Login Successful!");
		System.out.println("Executing Login Test");
	}

	@Test(dataProvider = "getData", priority = 1)
	public void Workflow(String un, String pwd, String qpid) {

		test = extent.createTest("Workflow with QP-ID:" + qpid);

		System.out.println("Executing Login Test with " + un + " and " + pwd);
		if (qpid.equals("102")) {
			Assert.fail("Workflow Failed with QP-ID: " + qpid);
		} else if (qpid.equals("104")) {
			throw new SkipException("Skipping the worlflow with QP-ID:" + qpid);
		}
	}

	/*
	 * @Test public void isSkip() {
	 * 
	 * throw new SkipException("Skipping the test case"); }
	 */

	@DataProvider
	public Object[] getData() {
		Object data[][] = new Object[6][3];
		data[0][0] = "admin";
		data[0][1] = "pwd-admin";
		data[0][2] = "100";

		data[1][0] = "padmin";
		data[1][1] = "pwd-padmin";
		data[1][2] = "101";

		data[2][0] = "gadmin";
		data[2][1] = "pwd-gadmin";
		data[2][2] = "102";

		data[3][0] = "admin";
		data[3][1] = "pwd-admin";
		data[3][2] = "103";

		data[4][0] = "padmin";
		data[4][1] = "pwd-padmin";
		data[4][2] = "104";

		data[5][0] = "gadmin";
		data[5][1] = "pwd-gadmin";
		data[5][2] = "105";

		return data;
	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {

			String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());
			test.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
					+ "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>"
					+ " \n");

			/*
			 * try {
			 * 
			 * ExtentManager.captureScreenshot(); testReport.get().fail("<b>" +
			 * "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
			 * MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName)
			 * .build()); } catch (IOException e) {
			 * 
			 * }
			 */

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
