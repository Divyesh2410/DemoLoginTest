package utils;

import java.util.Properties;
import java.io.FileInputStream;

public class ConfigReader {

	public static Properties prop;

	public static Properties loadconfig() {
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;

	}

}
