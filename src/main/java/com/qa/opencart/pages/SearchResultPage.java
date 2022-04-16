package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class SearchResultPage {
	private WebDriver driver;
	ElementUtil elementUtil;
	
	private By searchResult=By.cssSelector("div.product-thumb");
	private By resultItems=By.cssSelector("div.product-thumb h4 a");
	
	public SearchResultPage(WebDriver driver) {
		this.driver=driver;
		elementUtil = new ElementUtil(driver);
	}
	
	public int getProductResultCount() {
		return elementUtil.getElements(searchResult).size();
	}
	
	public ProductInfoPage selectProductFromList(String productName) {
		List<WebElement> resultItemList=elementUtil.getElements(resultItems);
		System.out.println("total number of items displayed are : " +productName 
				+ " "+ resultItemList.size());
		for(WebElement e:resultItemList) {
			if(e.getText().equals(productName)){
				e.click();
				break;
			
			}
		}
		return new ProductInfoPage(driver);
	}
}
