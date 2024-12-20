package api.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extentReports;
	public ExtentTest test;

	String repName;

	
	public void onStart(ITestContext context) {

		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timestamp + ".html";

		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

		sparkReporter.config().setDocumentTitle("API Test Report");
		sparkReporter.config().setReportName("Pet Store API Tests");
		sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

		extentReports = new ExtentReports();
		extentReports.attachReporter(sparkReporter);
		extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
		extentReports.setSystemInfo("Application", "Pet Store API");
		extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
		extentReports.setSystemInfo("user", "Bipasha");
	}

	public void onTestSuccess(ITestResult result) {
    	 test=extentReports.createTest(result.getName());
     	 test.createNode(result.getName());
     	 test.assignCategory(result.getMethod().getGroups());
     	 test.log(Status.PASS,"Test passed");
        
    }

	public void onTestFailure(ITestResult result) {
		test = extentReports.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, "Test failed");
		test.log(Status.FAIL, result.getThrowable().getMessage());
	}

	public void onTestSkipped(ITestResult result) {
		test = extentReports.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, "Test skipped");
		test.log(Status.SKIP, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext context) {

		extentReports.flush();
	}
}
