package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	WebDriver driver;
	WebDriverWait wait;

	// constructor:
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// locators:
	private By userNameLoc = By.xpath("//input[@placeholder='Username']");
	private By passwordLoc = By.xpath("//input[@placeholder='Password']");
	private By BtnLoc = By.xpath("//button[normalize-space()='Login']");
	
	private By errorLoc = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");

	// methods
	// 1. Sendkeys user name to the user name field:

	public void setUserName(String UserName) {
		WebElement txt_UserName = wait.until(ExpectedConditions.elementToBeClickable(userNameLoc));
		txt_UserName.sendKeys(UserName);

	}

	public void setPassword(String Password) {
		WebElement txt_Password = wait.until(ExpectedConditions.elementToBeClickable(passwordLoc));
		txt_Password.sendKeys(Password);

	}

	public void click_LoginBtn() {
		WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(BtnLoc));
		loginBtn.click();
	}
	
	//for invalid credential, check if the error message is displayed or not:
	public boolean isErrorMessageDisplayed() {
		WebElement errMessg = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLoc));
        return errMessg.isDisplayed(); //return true if the error message is displayed
    }
	
	//get the error message when it is displayed:
	public String getErrorMessage() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLoc));
        return errorMsg.getText();
    }

}
