package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class RegistrationPageTets extends BaseTest{

	
	@BeforeClass
	public void setUpRegister() {
		registPage=loginPage.navigatetoRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegisterData() {
		Object regData[][]=ExcelUtil.getSheetData(Constants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	public String getRandomGenerator() {
		Random randomGenerator = new Random();
		String email="testautomation1" + randomGenerator.nextInt(1000)+"@gmail.com";
		return email;
	}
	
	@Test(dataProvider="getRegisterData")
	public  void userRegistrationTest(String firstname,String lastname,
										String phone,String password,String	subscribe) {
		Assert.assertTrue(registPage.accountRegistration(firstname,lastname,getRandomGenerator(),
														phone,password,subscribe));
	}
	
}
