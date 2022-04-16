package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**@author Sowmya***/


public class DriverFactory {

	WebDriver driver;
	Properties prop;
	private OptionsManager optionsManager;
	
	public static String highlight =null;
	public static ThreadLocal<WebDriver> tlDriver= new ThreadLocal<>();
	
/*@prope browser name 
 * @return this method will return webdriver */	
	
	
	public WebDriver init_driver(Properties  prop) {
		
		highlight=prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		String browserName=prop.getProperty("browser").trim();
		System.out.println("browser name is :" +browserName);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();	
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions())); 
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			
		}
		
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver();
			tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
		}
		
		else if(browserName.equalsIgnoreCase("safari")) {
			///driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		}
		
		else {
			System.out.println("please pass the correct browser" + browserName);
		}
		
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url").trim());
		
		return getDriver();
	}
	
	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}
	
	/*
	 * @prop return properties which are loaded from config.properties
	 */
	
	public Properties init_prop() {
		FileInputStream ip = null;
		prop = new Properties();
		
		String env = System.getProperty("env");
				
		if(env==null) {
			System.out.println("Running on Environment : --> on Pro");
			try {
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Running on Environment : -->" +env);
			try {
			switch (env) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			default:
				break;
			}
		}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		try {
			
			prop.load(ip);
		} 
				
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
		return prop;
	}
	
	/**Take screenshot method**/
	   public String getScreenshot() {
		File src= ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+"/screenshots/"+System.currentTimeMillis()+".png";
		File destination =new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	   }
}
