package automacao.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class PropertiesUtils {

	private static Properties propertie = new Properties();
	private static String PATH_PROPERTIES = System.getProperty("user.dir")+"/src/test/resources/datapool/config.properties";
	
	public static String getValue(String name) throws IOException {
		propertie.load(new FileInputStream(PATH_PROPERTIES));
		return propertie.getProperty(name);
	}
	
	public static void getConfigFile(String path) throws FileNotFoundException, IOException {
		propertie.load(new FileInputStream(PATH_PROPERTIES));
		PropertyConfigurator.configure(propertie);
	}
}
