package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.MyExtentReport;

public class BaseTest {
	protected WebDriver driver;
	protected WebDriverWait wait;
	
  @BeforeMethod
  public void init() {
	  
	  driver = new ChromeDriver();
	 // Set WebDriver instance for MyExtentReport (for screenshot generation)
	  MyExtentReport.setDriver(driver); 
	  
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	  driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	  
  }

  @AfterMethod
  public void teardown() {
      if (driver != null) {
          driver.quit();
      }
  }

}
