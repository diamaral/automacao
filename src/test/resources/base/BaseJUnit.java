package automacao.base;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import automacao.utils.Browsers;
import automacao.utils.PropertiesUtils;
import automacao.utils.StatusReport;

public class BaseJUnit {
	public static WebDriver driver;
	public ExtentTest extentTest; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Browsers.openBrowser(PropertiesUtils.getValue("grid"), PropertiesUtils.getValue("browser"));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(PropertiesUtils.getValue("ambiente"));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
		extentTest = StatusReport.startLogTest(this.getClass().getSimpleName());
		extentTest.info(PropertiesUtils.getValue("ambiente"));
	}
}
