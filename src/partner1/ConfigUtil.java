package partner1;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	
	Properties prop; 
	
	public ConfigUtil(){
		InputStream input = null;
		prop = new Properties();
		
		String propFileName = "config.properties";

		input = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (input != null) {
				try {
					prop.load(input);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public String getConfig(String name){
		return prop.getProperty(name);
	}
	
	public static void main(String[] argv){
		ConfigUtil tool = new ConfigUtil();
		System.out.println("User: " + tool.getConfig("user"));
	}
}
