package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	public WebElement getElement(By locators) {
		WebElement element =driver.findElement(locators);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public List<WebElement> getElements(By locators) {
		return driver.findElements(locators);
	}

	public void doSendKeys(By locators, String value) {
	WebElement element=	getElement(locators);
	element.clear();
	element.sendKeys(value);		
	}

	public void doClick(By locators) {
		getElement(locators).click();
	}

	public String doGetText(By locators) {
		return getElement(locators).getText();
	}

	public boolean doISDisplayed(By locators) {
		return getElement(locators).isDisplayed();
	}

	public List<String> getElementsTextList(By locators) {
		List<String> eleText = new ArrayList<String>();

		List<WebElement> eleList = getElements(locators);
		for (WebElement e : eleList) {
			if (!e.getText().isEmpty()) {
				eleText.add(e.getText());
			}
		}
		return eleText;
	}
	
	public void doActionsMoveToElement(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).perform();
	}
	public void doActionsMoveToElementAndClick(By locator) {
		doActionsMoveToElement(locator);
		getElement(locator).click(); 
		
	}

	/**************** Dropdown utils **********/

	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}
	
	public String getPageUrl() {
		return driver.getCurrentUrl();
	}

	/******* using getoption using select class *******/
	public void doSelectDropdownValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	/***** select dopdown value without using select class ***/
	public void doSelectDropdownWithoutSelectClass(By locator, String value) {
		List<WebElement> list = getElements(locator);

		for (WebElement e : list) {
			if (e.getText().equals("India")) {
				e.click();
				break;
			}

		}
	}

	public String switchToWindowGetText(String windowId) {
		driver.switchTo().window(windowId);
		String title = driver.getTitle();
		System.out.println(title);
		String url = driver.getCurrentUrl();
		System.out.println(url);
		return url;
	}
	
	/**************** Wait Utils********************/
	
	public Alert waitForAlertPresent(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.alertIsPresent());		
	}
	public String getAlertText(int timeOut) {
		return waitForAlertPresent(timeOut).getText();
	}
	public void acceptAlert(int timeOut) {
		waitForAlertPresent(timeOut).accept();
	}
	public void dismissAlert(int timeOut) {
		waitForAlertPresent(timeOut).dismiss();
	}
	
	public String waitForTitle(int timeOut, String title) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.titleIs(title));
		return driver.getTitle();
	}
	public String waitForTitleContains(int timeOut, String title) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.titleContains(title));
		return driver.getTitle();
	}
	public String waitForTitle(int timeOut, String title,int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut, intervalTime);
		wait.until(ExpectedConditions.titleIs(title));
		return driver.getTitle();
	}
	public boolean waitForUrl(int timeOut, String url) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.urlContains(url));
		 
	}
	
	public void waitForFrameAndSwitchToIt(String idOrName, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}
	
	public void waitForFrameAndSwitchToIt(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	
	public void waitForFrameAndSwitchToIt(int index, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	}
	
	public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	/*
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible
	 */
	public WebElement waitForElementPresent(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	/***An expectation for checking that an element is present 
	 * on the DOM of a page and visible.Visibility means that the element is not
	 *  only displayed but also has a height and width that isgreater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
		
	public List<WebElement> waitForVisibilityOfElements(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public void printElementText(By locator,int timeOut) {
		waitForVisibilityOfElements(locator, timeOut)
			.stream()
				.forEach(ele->System.out.println(ele.getText()));
	}
	
	public void printListElement(List<String> eleList) {
		eleList.forEach(ele->System.out.println(ele));
	}
	
	public List<String> getElementsListWithText(By locator,int timeOut,String filterValue) {
		return waitForVisibilityOfElements(locator, timeOut)
			.stream()
				.filter(ele->ele.getText().contains(filterValue))
					.map(ele->ele.getText())
						.collect(Collectors.toList());
	}
	
	public List<WebElement> getElementsList(By locator,int timeOut,String filterValue) {
		return waitForVisibilityOfElements(locator, timeOut)
			.stream()
				.filter(ele->ele.getText().contains(filterValue))					
					.collect(Collectors.toList());
	}
		
	//This method is specificaly for tagName
	public List<WebElement> waitForPresenceOfElements(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	public List<String> getEleTextList(By locator,int timeOut) {
		List<String> eleTextList = new ArrayList<String>();
		List<WebElement> eleList=waitForVisibilityOfElements(locator, timeOut);
		for(WebElement e:eleList) {
			if(!e.getText().isEmpty()) {
				eleTextList.add(e.getText());
			}
		}
		return eleTextList;
	}
	/**An expectation for checking an element is 
	 * visible and enabled such that you can click it.**/
	
	public  WebElement waitForElementToBeClickable(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public void clickWhenReady(By locator, int timeOut) {
		waitForElementToBeClickable(locator, timeOut).click();
	}
	
	public WebElement waitForElementWithFluentWait(int timeOut,long pollingTime,By locator) {
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(pollingTime))
				.ignoring(NoSuchElementException.class);
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public Alert waitForAlertWithFluentWait(int timeOut,long pollingTime) {
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(pollingTime))
				.ignoring(NoAlertPresentException.class);
		
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public WebDriver waitForFrameWithFluentWait(String frameLocator,int timeOut,long pollingTime) {
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(pollingTime))
				.ignoring(NoAlertPresentException.class);
		
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
}
