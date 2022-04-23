package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	//1.private by locators 
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@type='submit']");
	private By forgotPasswordLink=By.xpath("//div[@class='form-group']/a[text()='Forgotten Password']");
	private By registerLink = By.linkText("Register");
	private By loginErrorMessage= By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	
	
	//2.constructor 	
	public LoginPage(WebDriver driver) {
		this.driver= driver;
		elementUtil = new ElementUtil(driver);
	} 
	
	//3.actions
	
	@Step("getting login page title")
	public String getLoginPageTitle() {
		return elementUtil.waitForTitle(9, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Step("getting login page url")
	public String getLoginPageUrl() {
		 return elementUtil.getPageUrl();
	}
	
	@Step("getting Forgot Password is Exist")
	public boolean isForgotPasswordExist() {
		return elementUtil.doISDisplayed(forgotPasswordLink);
	}
	
	@Step("login with username: {0} and password: {1}")
	public AccountsPage  doLogin(String un,String pwd) {
	elementUtil.doSendKeys(username, un);
	elementUtil.doSendKeys(password, pwd);
	elementUtil.doClick(loginButton);
	
	return new AccountsPage(driver);
	}
	
	@Step("login with username: {0} and password: {1}")
	public boolean  doLoginWrongData(String un,String pwd) {
	elementUtil.doSendKeys(username, un);
	elementUtil.doSendKeys(password, pwd);
	elementUtil.doClick(loginButton);
	return elementUtil.doISDisplayed(loginErrorMessage);
		 
	}
	
	@Step("navigating to register page")
	public RegistrationPage navigatetoRegisterPage() {
		elementUtil.doClick(registerLink);
		return new RegistrationPage(driver);
	}
}
