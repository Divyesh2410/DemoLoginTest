package utils;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;

public class ConfigReader {

	public static Properties prop;

	public static Properties loadconfig() {
		try {
			prop = new Properties();
			
			// Try to load from file system first (for IDE execution)
			try {
				FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
				prop.load(fis);
				fis.close();
			} catch (Exception e) {
				// If file system path fails, try classpath (for Maven execution)
				InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
				if (is != null) {
					prop.load(is);
					is.close();
				}
			}
			
			// Override with system properties first (Maven -D flags), then environment variables (Jenkins parameters)
			// Priority: System Property > Environment Variable > config.properties
			String headless = System.getProperty("HEADLESS");
			if (headless == null || headless.isEmpty()) {
				headless = System.getenv("HEADLESS");
			}
			
			// Set headless property - explicitly default to "false" if not set
			if (headless != null && !headless.trim().isEmpty()) {
				prop.setProperty("headless", headless.trim().toLowerCase());
			} else {
				// Ensure default from properties file is used, or "false" if not found
				String defaultHeadless = prop.getProperty("headless", "false");
				prop.setProperty("headless", defaultHeadless.trim().toLowerCase());
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
