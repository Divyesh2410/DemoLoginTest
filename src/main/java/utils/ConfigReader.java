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
			
			// Override with system properties first (Maven -D flags), then environment variables (Jenkins parameters)
			// Priority: System Property > Environment Variable > config.properties
			String headless = System.getProperty("HEADLESS");
			if (headless == null || headless.isEmpty()) {
				headless = System.getenv("HEADLESS");
			}
			if (headless != null && !headless.isEmpty()) {
				prop.setProperty("headless", headless);
			}
			
			String url = System.getProperty("URL");
			if (url == null || url.isEmpty()) {
				url = System.getenv("URL");
			}
			if (url != null && !url.isEmpty()) {
				prop.setProperty("url", url);
			}
			
			String browser = System.getProperty("BROWSER");
			if (browser == null || browser.isEmpty()) {
				browser = System.getenv("BROWSER");
			}
			if (browser != null && !browser.isEmpty()) {
				prop.setProperty("browser", browser);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;

	}

}
