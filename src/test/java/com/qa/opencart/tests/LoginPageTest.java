package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic:Design login page for demo app")
@Story("US1:Develop feature with all login page scenarios")
public class LoginPageTest extends BaseTest{

	@Description("login Page TitleTest")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority =1)
	public void loginPageTitleTest() {
		String title=loginPage.getLoginPageTitle();
		System.out.println("loginpage title is : "+title);
		AssertJUnit.assertEquals(title,Constants.LOGIN_PAGE_TITLE );
	}
	
	@Description("login Page url test")
	@Severity(SeverityLevel.MINOR)
	@Test(priority =2,enabled =true)
	public void loinPageURLTest() {
		String url=loginPage.getLoginPageUrl();
		Assert.assertTrue(url.contains(Constants.LOGIN_URL_VALUE));
	}
	
	@Description("login Page ForgotPassword linktest")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =3)
	public void forgotPasswordLinkTest() {		
		Assert.assertTrue(loginPage.isForgotPasswordExist());
	}
	
	@Description("login Test")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority =4)
	public void loginTest() {
		loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}	

		
}
