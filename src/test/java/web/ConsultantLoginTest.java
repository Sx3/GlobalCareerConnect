package web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class ConsultantLoginTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Initialize the ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();

        // Navigate to the Consultant login page
        driver.get("http://localhost:8080/GCC_war_exploded/consultantlogin.jsp");
    }

    @Test
    public void testSuccessfulLogin() {
        login("VeraUSA22", "9014");
        assertEquals("http://localhost:8080/GCC_war_exploded/consultantdashboard.jsp", driver.getCurrentUrl());
    }

    @Test
    public void testUnsuccessfulLoginIncorrectUsername() {
        login("WrongUser", "ConsultantPassword");
        driver.switchTo().alert().dismiss();
        assertFailedLogin2();
    }

    @Test
    public void testUnsuccessfulLoginIncorrectPassword() {
        login("ConsultantUser", "WrongPassword");
        driver.switchTo().alert().dismiss();
        assertFailedLogin2();
    }

    @Test
    public void testUnsuccessfulLoginEmptyFields() {
        login("", "");
        assertFailedLogin();
    }

    @Test
    public void testUnsuccessfulLoginEmptyUsername() {
        login("", "ConsultantPassword");
        assertFailedLogin();
    }

    @Test
    public void testUnsuccessfulLoginEmptyPassword() {
        login("ConsultantUser", "");
        assertFailedLogin();
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
        assertEquals("http://localhost:8080/GCC_war_exploded/consultantlogin.jsp", driver.getCurrentUrl());
    }
    private void assertFailedLogin2() {
        assertEquals("http://localhost:8080/GCC_war_exploded/consultantLoginServlet", driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
