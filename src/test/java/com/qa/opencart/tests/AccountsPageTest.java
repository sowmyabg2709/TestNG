package com.qa.opencart.tests;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.Error;

public class AccountsPageTest extends BaseTest{
	
	
	@BeforeClass
	public void accPageSetUp() {
	accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	
	@Test
	public void accountPageTitleTest() {
		String title=accPage.getaccountPageTitle();
		System.out.println("Accountpage title is " + title);
		Assert.assertEquals(title, Constants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void accountPageHeaderTest() {
		String header=accPage.getAccountPageHeader();
		System.out.println("Account page header is : " +header);
		Assert.assertEquals(header, Constants.ACCOUNTS_PAGE_HEADER, Error.ACC_PAGE_TITLE_ERROR);
	}
	
	@Test
	public void accountSectionListTest() {
		List<String> sectList=accPage.getAccountSection();
		sectList.stream().forEach(e->System.out.println(e));
		Collections.sort(Constants.EXP_ACC_SECLIST);
		Assert.assertEquals(sectList, Constants.EXP_ACC_SECLIST);
	}
	
	@Test
	public void logOutLinkTest() {		
		Assert.assertTrue(accPage.isLogoutExist(),Error.ACC_LINK_NOT_PRESENT);
	}
	

	
}
