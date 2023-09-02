package web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JobSeekerRegistrationTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Initialize the ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();

        // Navigate to the Job Seeker Registration page
        driver.get("http://localhost:8080/GCC_war_exploded/jobSeekerRegister.jsp");
    }

    @Test
    public void testSuccessfulRegistration() {
        register("JohnDoe", "john@example.com", "1234567890", "JohnDoe", "password123", "password123");
        assertEquals("http://localhost:8080/GCC_war_exploded/jobseekerlogin.jsp", driver.getCurrentUrl());
    }

    @Test
    public void testUnsuccessfulRegistrationEmptyFields() {
        register("", "", "", "", "", "");
        assertFailedRegistration();
    }

    @Test
    public void testUnsuccessfulRegistrationMismatchedPasswords() {
        register("JohnDoe", "john@example.com", "1234567890", "JohnDoe", "password123", "password321");
        driver.switchTo().alert().dismiss();
        assertFailedRegistration();
    }

    private void register(String fullName, String email, String phone, String username, String password, String confirmPassword) {
        WebElement fullNameField = driver.findElement(By.id("fullName"));
        fullNameField.sendKeys(fullName);

        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys(email);

        WebElement phoneField = driver.findElement(By.id("phone"));
        phoneField.sendKeys(phone);

        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);

        WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
        confirmPasswordField.sendKeys(confirmPassword);

        WebElement registerButton = driver.findElement(By.tagName("button"));
        registerButton.click();
    }

    private void assertFailedRegistration() {

        // Get the current URL
        String currentUrl = driver.getCurrentUrl();

        // Check that the URL is one of the expected URLs
        assertTrue(currentUrl.equals("http://localhost:8080/GCC_war_exploded/jobSeekerRegister.jsp") ||
                currentUrl.equals("http://localhost:8080/GCC_war_exploded/jobSeekerRegister"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
