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

public class AdminBrowserTest {
	private static final String URL_PAGE = "http://localhost:8080/E-Commerce";
	
	private static final int MAX_PAGE_LOADING_TIME = 30000;
	private static final int MAX_IMPLICIT_WAIT = 10000;
	private static final int MAX_IMPLICIT_WAIT_JS = 5000;
	
	private static final String KEYS_USERNAME = "Daniel";
	private static final String KEYS_PASSWORD = "Daniel";
	private static final String NEW_ITEM_TITLE = "New Book Title";
	private static final String NEW_ITEM_DESCRIPTION = "New Book Description";
	
	private static final String AUTH_DOM_LOCATION = "/html/body/nav/div/div[@id='bs-example-navbar-collapse-1']/ul/li/a";
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
	public void authenticationTest() {
		driver.get(URL_PAGE);
		// click Login anchor
		driver.findElement(By.linkText("Login")).click();
		// input and submit username and password
		final WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(KEYS_USERNAME);
		final WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(KEYS_PASSWORD);
		inputPassword.submit();
		
		final WebElement logoutAnchor = driver.findElement(By.xpath(AUTH_DOM_LOCATION));
		AssertJUnit.assertEquals("Logout - User: " + KEYS_USERNAME,logoutAnchor.getText());
		
		// click Logout anchor
		logoutAnchor.click();
		final WebElement loginAnchor = driver.findElement(By.xpath(AUTH_DOM_LOCATION));
		AssertJUnit.assertEquals("Login", loginAnchor.getText());		
	}
	
	@Test
	public void adminRightsTest() {
		driver.get(URL_PAGE);
		// Login
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("inputUsername")).sendKeys(KEYS_USERNAME);
		final WebElement authentication = driver.findElement(By.id("inputPassword")); 
		authentication.sendKeys(KEYS_PASSWORD);
		authentication.submit();
		
		driver.findElement(By.id("dropdownMenu1")).click();
		driver.findElement(By.cssSelector(BOOK_CATEGORY_LINK)).click();
		
		AssertJUnit.assertEquals(1, driver.findElements(By.xpath("//a[contains(text(),'New Category')]")).size());
		AssertJUnit.assertEquals(1, driver.findElements(By.xpath("//a[contains(text(),'Change Category')]")).size());
		AssertJUnit.assertEquals(1, driver.findElements(By.xpath("//a[contains(text(),'New Item')]")).size());
		
		driver.findElement(By.cssSelector(BIKEBOOK_ITEM_LINK)).click();
		
		AssertJUnit.assertEquals(1, driver.findElements(By.xpath("//a[contains(text(),'Change Item')]")).size());		
	}
	
	@Test
	public void newItemTest() {
		driver.get(URL_PAGE);
		// click Login anchor
		driver.findElement(By.linkText("Login")).click();
		// input and submit username and password
		final WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(KEYS_USERNAME);
		final WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(KEYS_PASSWORD);
		inputPassword.submit();
		
		// go to books -> new item
		driver.findElement(By.id("dropdownMenu1")).click();
		driver.findElement(By.cssSelector(BOOK_CATEGORY_LINK)).click();
		int bookCount_before = driver.findElements(By.className("item")).size();
		driver.findElement(By.cssSelector("a[href='newItem.jsp']")).click();
		
		// input new book
		driver.findElement(By.id("inputTitle")).sendKeys(NEW_ITEM_TITLE);
		final WebElement itemDescription = driver.findElement(By.id("description"));
		itemDescription.sendKeys(NEW_ITEM_DESCRIPTION);
		itemDescription.submit();
		
		// redirected to home -> go to books
		driver.findElement(By.id("dropdownMenu1")).click();
		driver.findElement(By.cssSelector(BOOK_CATEGORY_LINK)).click();
		int bookCount_after = driver.findElements(By.className("item")).size();
		
		int newBookCount = driver.findElements(By.xpath("//a[contains(text(), '"+NEW_ITEM_TITLE+"')]")).size();
		
		AssertJUnit.assertEquals(bookCount_before + 1, bookCount_after);
		AssertJUnit.assertEquals(1, newBookCount);
	}

}
