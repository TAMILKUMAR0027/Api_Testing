package utils;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class LoadProperties {
	static Properties prop=new Properties();
	public static Properties getProp() {
	File file=new File("D:\\Assessment\\Lms_server\\src\\test\\resources\\url.properties");
	FileInputStream fileInput=null;
	try {
		fileInput=new FileInputStream(file);
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	try {
		prop.load(fileInput);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return prop;
	}
		
}
