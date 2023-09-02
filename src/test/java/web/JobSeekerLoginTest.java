package web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JobSeekerLoginTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Initialize the ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe"); // Update this path
        driver = new ChromeDriver();

        // Navigate to the login page
        driver.get("http://localhost:8080/GCC_war_exploded/jobseekerlogin.jsp");
    }


    @Test
    public void testUnsuccessfulLoginIncorrectUsername() {
        login("wrongUsername", "1289");
        driver.switchTo().alert().dismiss();
        assertFailedLogin();
    }

    @Test
    public void testUnsuccessfulLoginIncorrectPassword() {
        login("AliceB", "11111111111");
        driver.switchTo().alert().dismiss();
        assertFailedLogin();
    }

    @Test
    public void testUnsuccessfulLoginEmptyFields() {
        login("", "");
        assertFailedLogin();
    }

    @Test
    public void testUnsuccessfulLoginEmptyUsername() {
        login("", "1289");
        assertFailedLogin();
    }

    @Test
    public void testUnsuccessfulLoginEmptyPassword() {
        login("AliceB", "");
        assertFailedLogin();
    }

    @Test
    public void testSuccessfulLogin() {
        login("AliceB", "1289");
        assertEquals("http://localhost:8080/GCC_war_exploded/jobseekerdashboard.jsp", driver.getCurrentUrl());
    }

    private void login(String username, String password) {
        WebElement usernameField = driver.findElement(By.name("uname"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.name("psw"));
        passwordField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.tagName("button"));
        loginButton.click();
    }

    private void assertFailedLogin() {
        // Get the current URL
        String currentUrl = driver.getCurrentUrl();

        // Check that the URL is one of the expected URLs
        assertTrue(currentUrl.equals("http://localhost:8080/GCC_war_exploded/jobseekerlogin.jsp") ||
                currentUrl.equals("http://localhost:8080/GCC_war_exploded/jobSeekerLoginServlet"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
