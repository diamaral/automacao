package automacao.base;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.aventstack.extentreports.ExtentTest;

import automacao.utils.StatusReport;

public class JUnitListener extends RunListener {

	private ExtentTest extentTest; 

	public void testRunStarted(Description description) throws Exception {
	}

	public void testRunFinished(Result result) throws Exception {

	}

	public void testStarted(Description description) throws Exception {
	}

	public void testFinished(Description description) throws Exception {
		StatusReport.endReport().info(description.isTest());
	}

	public void testFailure(Failure failure) throws Exception {

	}

	public void testAssumptionFailure(Failure failure) {
	}

	public void testIgnored(Description description) throws Exception {
	}
}
