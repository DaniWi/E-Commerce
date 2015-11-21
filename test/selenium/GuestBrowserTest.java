package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GuestBrowserTest {
private static final String URL_PAGE = "http://localhost:8080/E-Commerce";
	
	private static final int MAX_PAGE_LOADING_TIME = 30000;
	private static final int MAX_IMPLICIT_WAIT = 10000;
	private static final int MAX_IMPLICIT_WAIT_JS = 5000;
	
	private static final String ELECTRONICS_CATEGORY_LINK = "a[href='category.jsp?categoryID=5']";
	private static final String LEDTV_ITEM_LINK = "a[href='item.jsp?id=12&categoryID=5']";

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
		
		AssertJUnit.assertTrue(true);
	}
	
	
	@Test(dependsOnMethods = { "initialLoadTest" })
	public void booksTest() {
		driver.get(URL_PAGE);
		
		// dropdown menu click has to be performed before
		driver.findElement(By.id("dropdownMenu1")).click();
		// click book category
		driver.findElement(By.cssSelector(ELECTRONICS_CATEGORY_LINK)).click();
		
		final WebElement header = driver.findElement(By.tagName("h2"));
		AssertJUnit.assertEquals("Electronics", header.getText());
		
		final int itemCount = driver.findElements(By.className("item")).size();
		AssertJUnit.assertEquals(2,itemCount);
	}
	
	@Test(dependsOnMethods = { "booksTest" })
	public void bikeBookTest() {
		driver.get(URL_PAGE);
		
		// dropdown menu click
		driver.findElement(By.id("dropdownMenu1")).click();
		// book category click
		driver.findElement(By.cssSelector(ELECTRONICS_CATEGORY_LINK)).click();
		// bike book click
		driver.findElement(By.cssSelector(LEDTV_ITEM_LINK)).click();
		
		final WebElement header = driver.findElement(By.tagName("h1"));
		AssertJUnit.assertEquals("Sony LED TV", header.getText());
		
		final int commentCount = driver.findElements(By.className("comment")).size();
		AssertJUnit.assertEquals(1,commentCount);
	}
}
