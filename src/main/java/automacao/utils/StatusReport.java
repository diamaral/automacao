package automacao.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class StatusReport {
	private static ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/report/extentReport.html");
	private static ExtentReports extent = new ExtentReports();
	private static Logger logger = LogManager.getLogger(StatusReport.class.getName());

	private static final String FILE_CONFIG_LOG4J = System.getProperty("user.dir")+"/src/test/resources/log4j.properties";

	public static ExtentTest startLogTest(String testName) {
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/src/test/resources/extent-config.xml");
		extent.attachReporter(htmlReporter);
		return extent.createTest(testName, "Automation Test");	
	}

	public static Logger endReport() throws FileNotFoundException, IOException {
		//DOMConfigurator.configure(FILE_CONFIG_LOG4J);
		PropertiesUtils.getConfigFile(FILE_CONFIG_LOG4J);
		extent.flush();
		return logger;
	}
}
