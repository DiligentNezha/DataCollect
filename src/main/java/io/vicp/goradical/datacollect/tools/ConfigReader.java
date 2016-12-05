package io.vicp.goradical.datacollect.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static final Properties CONFIG = new Properties();
	static {
		try {
			CONFIG.load(new FileInputStream(new File("src/main/resources/config/tomcat_jdbc.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return CONFIG.getProperty(key);
	}
}
