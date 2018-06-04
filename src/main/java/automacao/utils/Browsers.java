package automacao.utils;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browsers {
	private static WebDriver driver;
	private static DesiredCapabilities capabilities = new DesiredCapabilities();;
	private static Proxy proxy = new Proxy();


	public static WebDriver openBrowser(String grid, String browser) throws IOException {
		configProxy();
		if("".equalsIgnoreCase(grid)) {
			switch (browser) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				driver = new ChromeDriver(capabilities);
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				driver = new FirefoxDriver(capabilities);
				break;
			default:
				break;
			}
		}else {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			driver = new RemoteWebDriver(new URL(grid), capabilities);
		}
		return driver;
	}

	public static void configProxy() throws IOException {
		String configProxy = PropertiesUtils.getValue("proxy");
		String port = "";
		if(!configProxy.isEmpty()) {
			if("jmeter".equalsIgnoreCase(configProxy)) {
				port = "3128";
			}else {
				if("hoverfly".equalsIgnoreCase(configProxy)) {
					port  = "8500";
				}
			}
		}
		proxy.setHttpProxy("localhost:"+port); 
		proxy.setSslProxy("localhost:"+port);

		capabilities.setCapability(CapabilityType.PROXY, proxy);  
	}
}
