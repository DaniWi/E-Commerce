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

public class BrowserTestUser {
	
	private static final String URL_PAGE = "http://localhost:8080/E-Commerce";
	
	private static final int MAX_PAGE_LOADING_TIME = 30000;
	private static final int MAX_IMPLICIT_WAIT = 10000;
	private static final int MAX_IMPLICIT_WAIT_JS = 5000;
	
	private static final String KEYS_USERNAME = "Lukas";
	private static final String KEYS_PASSWORD = "Lukas";
	private static final String NEW_COMMENT_TEXT = "New Comment";
	
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
		Assert.assertEquals("Logout - User: " + KEYS_USERNAME,logoutAnchor.getText());
		
		// click Logout anchor
		logoutAnchor.click();
		final WebElement loginAnchor = driver.findElement(By.xpath(AUTH_DOM_LOCATION));
		Assert.assertEquals("Login", loginAnchor.getText());		
	}
	
	@Test
	public void userRightsTest() {
		driver.get(URL_PAGE);
		// Login
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("inputUsername")).sendKeys(KEYS_USERNAME);
		final WebElement authentication = driver.findElement(By.id("inputPassword")); 
		authentication.sendKeys(KEYS_PASSWORD);
		authentication.submit();
		
		driver.findElement(By.id("dropdownMenu1")).click();
		driver.findElement(By.cssSelector(BOOK_CATEGORY_LINK)).click();
		
		int buttonCount = driver.findElements(By.className("btn-primary")).size();
		Assert.assertEquals(3, buttonCount);
		
	}
	
	@Test
	public void newCommentTest() {
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts()
		.pageLoadTimeout(30000, TimeUnit.MILLISECONDS)
		.implicitlyWait(10000, TimeUnit.MILLISECONDS)
		.setScriptTimeout(5000, TimeUnit.MILLISECONDS);
		
		final String URL_PAGE = "http://localhost:8080/E-Commerce";
		
		driver.get(URL_PAGE);
		// click Login anchor
		driver.findElement(By.linkText("Login")).click();
		// input and submit username and password
		final WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys("Lukas");
		final WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys("Lukas");
		inputPassword.submit();
		
		// go to books -> new item
		driver.findElement(By.id("dropdownMenu1")).click();
		driver.findElement(By.cssSelector(BOOK_CATEGORY_LINK)).click();
		driver.findElement(By.cssSelector(BIKEBOOK_ITEM_LINK)).click();
		int commentCount_before = driver.findElements(By.className("comment")).size();
		// new comment button click
		driver.findElement(By.cssSelector("a[href='#endOfSite']")).click();
		
		// input new comment
		driver.findElement(By.id("commentTextArea")).sendKeys(NEW_COMMENT_TEXT);
		// save button click
		driver.findElement(By.xpath("//a[contains(text(), 'Save')]")).click();
		
		driver.navigate().refresh();
		
		int commentCount_after = driver.findElements(By.className("comment")).size();
		int newCommentCount = driver.findElements(By.xpath("//p[contains(text(), '"+NEW_COMMENT_TEXT+"')]")).size();
		
		Assert.assertEquals(commentCount_before + 1, commentCount_after);
		Assert.assertEquals(1, newCommentCount);
	}

}
