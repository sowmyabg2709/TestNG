package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddToCartPage {

	WebDriver driver;
	public AddToCartPage(WebDriver driver) {
		this.driver= driver;
	}
	
	private By cart = By.id("cart");
	public static void addedCart() {
		System.out.println("added to cart");
	}
}
