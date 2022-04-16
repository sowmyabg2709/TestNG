package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	 private WebDriver driver;
	 private ElementUtil elementUtil;
	
	private By accSections = By.cssSelector("div#content h2");
	private By header = By.cssSelector("div#logo a");
	private By logoutLink =By.linkText("Logout");
	private By searchField = By.name("search");
	private By searchButton = By.cssSelector("div#search button");
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	
	public String getaccountPageTitle() {
		return elementUtil.waitForTitle(5, Constants.ACCOUNTS_PAGE_TITLE);
	}
	
	public String getAccountPageURL() {
		return elementUtil.getPageUrl();
	}
	
	public String  getAccountPageHeader() {
		return elementUtil.doGetText(header);
	}
	
	public List<String> getAccountSection() {
		List<String> accSecListVal= new ArrayList<String>();
		List<WebElement> accSecList=elementUtil.waitForVisibilityOfElements(accSections, 5);
		for(WebElement e:accSecList ) {
			accSecListVal.add(e.getText());
		}
		Collections.sort(accSecListVal);
		return accSecListVal;
	}
	
	public boolean isLogoutExist() {
		return elementUtil.doISDisplayed(logoutLink);
	}
	
	public SearchResultPage doSearch(String productName) {
		System.out.println("Searching the product : " + productName);
		elementUtil.doSendKeys(searchField, productName);
		elementUtil.doClick(searchButton);
		
		return new SearchResultPage(driver);		
		
	}
}
