package web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.fail;

import static org.junit.Assert.assertEquals;

public class ConsultantRegisterTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Initialize the ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe"); // Update this path
        driver = new ChromeDriver();

        // Navigate to the registration page
        driver.get("http://localhost:8080/GCC_war_exploded/consultant_register.jsp");
    }

    @Test
    public void testSuccessfulRegistration() {
        register("JohnDoe", "john.doe@example.com", "1234567890", "UK", "IT", "johndoe", "password123", "password123");
        assertEquals("http://localhost:8080/GCC_war_exploded/consultantregister", driver.getCurrentUrl());
    }

    @Test
    public void testUnsuccessfulRegistrationMismatchedPasswords() {
        register("JohnDoe", "john.doe@example.com", "1234567890", "UK", "IT", "johndoe", "password123", "password124");
        try {
            // Switch to the alert box and assert
            Alert alert = driver.switchTo().alert();
            assertEquals("Passwords do not match!", alert.getText());
            alert.accept();  // Close the alert box
        } catch (NoAlertPresentException e) {
            // Alert not present, fail the test
            fail("Expected alert not found");
        }
        assertFailedRegistration();
    }


    private void register(String fullName, String email, String phone, String specCountry, String jobType, String username, String password, String confirmPassword) {
        WebElement fullNameField = driver.findElement(By.name("fname"));
        fullNameField.sendKeys(fullName);

        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys(email);

        WebElement phoneField = driver.findElement(By.name("phone"));
        phoneField.sendKeys(phone);

        // Handle dropdowns for Specialized Country and Specialized Job Type
        WebElement specCountryDropdown = driver.findElement(By.name("spec_country"));
        specCountryDropdown.sendKeys(specCountry);

        WebElement jobTypeDropdown = driver.findElement(By.name("job_type"));
        jobTypeDropdown.sendKeys(jobType);

        WebElement usernameField = driver.findElement(By.name("uname"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.name("psw"));
        passwordField.sendKeys(password);

        WebElement confirmPasswordField = driver.findElement(By.name("confirmPsw"));
        confirmPasswordField.sendKeys(confirmPassword);

        WebElement registerButton = driver.findElement(By.tagName("button"));
        registerButton.click();
    }

    private void assertFailedRegistration() {
        // Check that the URL didn't change
        assertEquals("http://localhost:8080/GCC_war_exploded/consultantregister", driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
