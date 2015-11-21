package selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class sandbox {

	private static final String URL_PAGE = "http://localhost:8080/E-Commerce";
	
	private static final int MAX_PAGE_LOADING_TIME = 30000;
	private static final int MAX_IMPLICIT_WAIT = 10000;
	private static final int MAX_IMPLICIT_WAIT_JS = 5000;
	
	private static final String KEYS_USERNAME = "Lukas";
	private static final String KEYS_PASSWORD = "Lukas";
	private static final String NEW_ITEM_TITLE = "New Book Title";
	private static final String NEW_ITEM_DESCRIPTION = "New Book Description";
	
	private static final String AUTH_DOM_LOCATION = "/html/body/nav/div/div[@id='bs-example-navbar-collapse-1']/ul/li/a";
	private static final String BOOK_CATEGORY_LINK = "a[href='category.jsp?categoryID=3']";
	private static final String NEW_COMMENT_TEXT = "New Comment";
	private static final String BIKEBOOK_ITEM_LINK = "a[href='item.jsp?id=23&categoryID=3']";
	private static final String CLOTHING_CATEGORY_LINK = "a[href='category.jsp?categoryID=4']";
	private static final String TSHIRT_ITEM_LINK = "a[href='item.jsp?id=9&categoryID=4']";
	
	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts()
		.pageLoadTimeout(30000, TimeUnit.MILLISECONDS)
		.implicitlyWait(10000, TimeUnit.MILLISECONDS)
		.setScriptTimeout(5000, TimeUnit.MILLISECONDS);
		
		final String URL_PAGE = "http://localhost:8080/E-Commerce";
		
		driver.get(URL_PAGE);
		// Login
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("inputUsername")).sendKeys(KEYS_USERNAME);
		final WebElement authentication = driver.findElement(By.id("inputPassword")); 
		authentication.sendKeys(KEYS_PASSWORD);
		authentication.submit();
		
		driver.findElement(By.id("dropdownMenu1")).click();
		driver.findElement(By.cssSelector(CLOTHING_CATEGORY_LINK)).click();
		driver.findElement(By.cssSelector(TSHIRT_ITEM_LINK)).click();
		
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'New Comment')]"));
		System.out.println(list.size());
		list = driver.findElements(By.xpath("//a[contains(text(),'New Item')]"));
		System.out.println(list.size());
		list = driver.findElements(By.xpath("//a[contains(text(),'Change Item')]"));
		System.out.println(list.size());
		
		driver.close();
	}

}
