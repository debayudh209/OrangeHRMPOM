package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

	@Test(dataProvider = "logindata")
	public void testLogin(String userName, String Password, boolean isValidUser) {

		// create object of loginPage
		LoginPage loginPage = new LoginPage(driver);
		// enter userName, enter password, click loginbtn:--> call the methods
		loginPage.setUserName(userName);
		loginPage.setPassword(Password);
		loginPage.click_LoginBtn();

		if (isValidUser) // for valid login, user will be on home page. Assert the home page url:
		{
			// create home page obj
			HomePage homePage = new HomePage(driver);

			// assert that the home page URL is displayed
			Assert.assertTrue(homePage.isHomePageDisplayed(), "Login failed for valid credentials"); // If the Boolean
																										// value is
																										// true, then
																										// the assertion
																										// passes the
																										// test case.

			// now logout:
			homePage.Logout();

		}
		// if the credential is not valid
		else {
			// assert that for invalid credential, the error message is displayed on the
			// login screen:
			Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed for invalid user");
			System.out.println("Login failed with message: " + loginPage.getErrorMessage());
		}

	}// end of the @test

	@DataProvider
	public Object[][] logindata() {
		return new Object[][] { { "Admin", "admin123", true }, // Valid Credentials
				{ "Admin", "wrongPass", false }, // Invalid Password
				{ "wrongUser", "admin12345", false } // Invalid Username

		};
	}

}
