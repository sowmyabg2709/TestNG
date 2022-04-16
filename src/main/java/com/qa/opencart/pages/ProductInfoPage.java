/**
 * 
 */
package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

/**
 * @author Sowmya
 *
 */
public class ProductInfoPage {
	private WebDriver driver;
	ElementUtil elementUtil;

	private By productHeader = By.cssSelector("div#content h1");
	private By productImage = By.cssSelector("ul.thumbnails li");
	private By productMetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
	private By productPriceData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");

	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By successMessage = By.cssSelector("div.alert.alert-success.alert-dismissible");

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	public String getProductHeaderText() {
		return elementUtil.doGetText(productHeader);
	}

	public int getProductImagesCount() {
		return elementUtil.getElements(productImage).size();
	}

	/**
	 * This method will collect the product metadata and pricing data information in
	 * the form of Hashmap This method will return productinfoMap 
	 * @return *
	 */

	public Map<String, String> getProductInfromation() {
		Map<String, String> productInfoMap = new HashMap<String, String>();
		productInfoMap.put("name", getProductHeaderText());

		List<WebElement> metaDataList = elementUtil.getElements(productMetaData);
		System.out.println("total product meta data : " + metaDataList.size());

		// metadata
		for (WebElement e : metaDataList) {
			// Brand: Apple
			String meta[] = e.getText().split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productInfoMap.put(metaKey, metaValue);
		}
		List<WebElement> priceList = elementUtil.getElements(productPriceData);
		System.out.println("total product price data : " + priceList.size());
		String price = priceList.get(0).getText().trim();
		String exPrice = priceList.get(1).getText().trim();

		productInfoMap.put("price", price);
		productInfoMap.put("ExTaxPrice", exPrice);
		return productInfoMap;
	}

	public void selectQuantity(String qty) {
		elementUtil.doSendKeys(quantity, qty);
	}

	public void addToCart() {
		elementUtil.doClick(addToCartBtn);
	}

	public String isSuccessMessageDisplayed() {
		return elementUtil.doGetText(successMessage);
	}
	
}
