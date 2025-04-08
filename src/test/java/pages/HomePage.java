package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	WebDriver driver;
	WebDriverWait wait;

	// constructor
	public HomePage(WebDriver driver) {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// locators:

	private By profileImg = By.xpath("//img[@class='oxd-userdropdown-img']");
	private By logOutLoc = By.xpath("//a[normalize-space()='Logout']");

//Verify if the home page URL is correct after login
	public boolean isHomePageDisplayed() {
		return wait.until(ExpectedConditions.urlContains("dashboard"));
	}

	// for Logout: The return type is LoginPage as after we Logout, we navigate to
	// Login page
	public LoginPage Logout() {
		WebElement profilePic = wait.until(ExpectedConditions.elementToBeClickable(profileImg));
		profilePic.click();

		WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(logOutLoc));
		logoutLink.click();

		return new LoginPage(driver); // returns a new LoginPage object

	}

}
