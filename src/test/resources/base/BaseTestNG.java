package automacao.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.LogStatus;

import automacao.utils.Browsers;
import automacao.utils.ExtentManager;
import automacao.utils.ExtentTestManager;
import automacao.utils.PropertiesUtils;
import automacao.utils.ScreenShotUtils;

public class BaseTestNG {
	public WebDriver driver;

	@BeforeMethod
	public void beforeMethod(Method method) throws IOException {
		driver.get(PropertiesUtils.getValue("ambiente"));
		ExtentTestManager.startTest(method.getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws FileNotFoundException, IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
		} else {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
		}		
		ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(ScreenShotUtils.captureImage(driver)));
		ExtentManager.getReporter().endTest(ExtentTestManager.getTest());        
		ExtentManager.getReporter().flush();
	}

	protected String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		driver = Browsers.openBrowser(PropertiesUtils.getValue("grid"), PropertiesUtils.getValue("browser"));
	}

	@AfterClass
	public void afterClass() throws IOException {
		ScreenShotUtils.captureImage(driver);
		driver.quit();
	}

	@BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() throws FileNotFoundException, IOException {

	}

	@BeforeSuite
	public void beforeSuite() {
	}

	@AfterSuite
	public void afterSuite() {
	}
}
