package selenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;

public class NewTest {
	private static final String URL_PAGE = "http://localhost:8080/E-Commerce";
	
	private static final int MAX_PAGE_LOADING_TIME = 30000;
	private static final int MAX_IMPLICIT_WAIT = 10000;
	private static final int MAX_IMPLICIT_WAIT_JS = 5000;
	
	private static final String KEYS_USERNAME = "King Kong";
	private static final String KEYS_PASSWORD = "banana";
	private static final String KEYS_EMAIL = "king@kong.ba";
	
	private static final String REGISTER_LINK = "a[href='register.html']";
	
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
	public void successfulRegistrationTest() {
		driver.get(URL_PAGE);
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.cssSelector(REGISTER_LINK)).click();
		
		final WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(KEYS_USERNAME);
		final WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(KEYS_PASSWORD);
		final WebElement inputEmail = driver.findElement(By.id("inputEmail"));
		inputEmail.sendKeys(KEYS_EMAIL);
		driver.findElement(By.id("radios-0")).click();
		inputEmail.submit();
		
		String headline = driver.findElement(By.tagName("h1")).getText();
		AssertJUnit.assertEquals("Hello World, this is Webshop 4.", headline);
	}
	
	@Test(dependsOnMethods = { "successfulRegistrationTest" })
	public void unsuccessfulRegistrationTest() {
		driver.get(URL_PAGE);
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.cssSelector(REGISTER_LINK)).click();
		
		final WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(KEYS_USERNAME);
		inputUsername.submit();
		
		AssertJUnit.assertEquals("http://localhost:8080/E-Commerce/register.html", driver.getCurrentUrl());
		
	}

}
