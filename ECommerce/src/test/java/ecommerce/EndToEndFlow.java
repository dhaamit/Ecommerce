package ecommerce;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EndToEndFlow {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("shanidev@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Shanidev123");
		driver.findElement(By.id("login")).click();

		List<WebElement> products = new ArrayList<WebElement>();
		WebElement product = null;
		boolean verification = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.cssSelector(".col-lg-4"))));

		products = driver.findElements(By.cssSelector(".mb-3"));

		for (WebElement productIter : products) {
			if (productIter.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")) {
				product = productIter;
				// System.out.println("inside");

			}
		}

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".fa.fa-shopping-cart"))));
		product.findElement(By.cssSelector(".fa.fa-shopping-cart")).click();

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#toast-container"))));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		String verificationText = driver.findElement(By.cssSelector("#toast-container")).getText();
		Assert.assertEquals(verificationText, "Product Added To Cart");

		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".wrap.cf")));

		products = null;
		products = driver.findElements(By.cssSelector(".cartWrap.ng-star-inserted"));

		for (WebElement productIter : products) {
			System.out.println(productIter.findElement(By.cssSelector("h3")).getText());
			if (productIter.findElement(By.cssSelector("h3")).getText().equals("ZARA COAT 3")) {
				verification = true;
				product = productIter;
				System.out.println("inside");
			}
		}
		Assert.assertEquals(verification, true);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".totalRow button")));
		driver.findElement(By.cssSelector(".totalRow button")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[placeholder='Select Country']")));

		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("//button[@type='button'][2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();

		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANK YOU FOR THE ORDER."));
		driver.close();
	}
}
