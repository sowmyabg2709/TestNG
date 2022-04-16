package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultPage;
import com.qa.opencart.utils.JavaScriptUtil;

@Listeners(TestAllureListener.class)
public class BaseTest {
	
	DriverFactory df;
	public WebDriver driver;
	public Properties prop;
	JavaScriptUtil jsUtil;
	
	public LoginPage loginPage;
	public AccountsPage accPage;
	public SearchResultPage searchResPage;
	public ProductInfoPage productInfoPage;
	public RegistrationPage registPage;
	
	
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(String browserName) {
		df = new DriverFactory();
		prop=df.init_prop();
		prop.setProperty("browser", browserName);
		driver =df.init_driver(prop);		
		loginPage =new LoginPage(driver);
		//jsUtil = new JavaScriptUtil(driver);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
