package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class BrowserTestGuest {
	
	private static final String URL_PAGE = "http://localhost:8080/E-Commerce";
	
	private static final int MAX_PAGE_LOADING_TIME = 30000;
	private static final int MAX_IMPLICIT_WAIT = 10000;
	private static final int MAX_IMPLICIT_WAIT_JS = 5000;
	
	private static final String BOOK_CATEGORY_LINK = "a[href='category.jsp?categoryID=3']";
	private static final String BIKEBOOK_ITEM_LINK = "a[href='item.jsp?id=23&categoryID=3']";

	private WebDriver driver;

	@BeforeMethod
	public void startBrowser() {
		driver = new FirefoxDriver();
		driver.manage().timeouts()
			.pageLoadTimeout(MAX_PAGE_LOADING_TIME, TimeUnit.MILLISECONDS)
			.implicitlyWait(MAX_IMPLICIT_WAIT, TimeUnit.MILLISECONDS)
			.setScriptTimeout(MAX_IMPLICIT_WAIT_JS, TimeUnit.MILLISECONDS);
	}

	@AfterMethod
	public void closeBrowser() {
		if (driver != null) {
			driver.close();
		}
	}

	
	@Test
	public void initialLoadTest() {
		driver.get(URL_PAGE);
		
		Assert.assertTrue(true);
	}
	
	
	@Test
	public void booksTest() {
		driver.get(URL_PAGE);
		
		// dropdown menu click has to be performed before
		driver.findElement(By.id("dropdownMenu1")).click();
		// click book category
		driver.findElement(By.cssSelector(BOOK_CATEGORY_LINK)).click();
		
		final WebElement header = driver.findElement(By.tagName("h2"));
		Assert.assertEquals("Books", header.getText());
		
		final int itemCount = driver.findElements(By.className("item")).size();
		Assert.assertEquals(2,itemCount);
	}
	
	@Test
	public void bikeBookTest() {
		driver.get(URL_PAGE);
		
		// dropdown menu click
		driver.findElement(By.id("dropdownMenu1")).click();
		// book category click
		driver.findElement(By.cssSelector(BOOK_CATEGORY_LINK)).click();
		// bike book click
		driver.findElement(By.cssSelector(BIKEBOOK_ITEM_LINK)).click();
		
		final WebElement header = driver.findElement(By.tagName("h1"));
		Assert.assertEquals("Bike book", header.getText());
		
		final int commentCount = driver.findElements(By.className("comment")).size();
		Assert.assertEquals(2,commentCount);
	}

}
