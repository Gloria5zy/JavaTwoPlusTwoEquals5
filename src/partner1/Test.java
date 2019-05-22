package partner1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class Test {

	public void work(){
		InputStream input = null;
		
		Properties prop = new Properties();
		String propFileName = "config.properties";

		input = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (input != null) {
			try {
				prop.load(input);
				int available = input.available();
				System.out.println("Available: " + available);
				String user = prop.getProperty("user");
				String company1 = prop.getProperty("company1");
				String company2 = prop.getProperty("company2");
				String company3 = prop.getProperty("company3");
	 
				Date time = new Date(System.currentTimeMillis());
				String result = "Company List = " + company1 + ", " + company2 + ", " + company3;
				System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
				input.close();
			} catch (Exception e) {
				System.out.println("Exception: " + e);
			} finally {
			}
		}
	}
	
	public static void main(String[] args) {
		Test test = new Test();
		test.work();
	}
}
