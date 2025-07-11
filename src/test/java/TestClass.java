import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;


public class TestClass {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
    }

    //    @Test
    public void explicitWait() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Allure.step("Go to page");
        driver.get("https://practicetestautomation.com/practice-test-login/");
        driver.findElement(By.xpath("//input[@id=\"username\"]")).sendKeys("student");
        driver.findElement(By.xpath("//input[@id=\"password\"]")).sendKeys("Password123");
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id=\"submit1\"]")));
        element.click();
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()=\"Log out\"]")).isDisplayed(), "Log out button is not displayed");
    }

    //    @Test
    public void implicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Allure.step("Go to page");
        driver.get("https://practicetestautomation.com/practice-test-login/");
        driver.findElement(By.xpath("//input[@id=\"username\"]")).sendKeys("student");
        driver.findElement(By.xpath("//input[@id=\"password1\"]")).sendKeys("Password123");
        driver.findElement(By.xpath("//button[@id=\"submit\"]")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()=\"Log out\"]")).isDisplayed(), "Log out button is not displayed");
    }

    @Test
    public void assertIf() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Allure.step("Go to page");
        driver.get("https://practicetestautomation.com/practice-test-login/");
        driver.findElement(By.xpath("//input[@id=\"username\"]")).sendKeys("incorrectUser");
        driver.findElement(By.xpath("//input[@id=\"password\"]")).sendKeys("Password123");
        driver.findElement(By.xpath("//button[@id=\"submit\"]")).click();

        boolean isDisplay = driver.findElement(By.id("error")).isDisplayed();
        if (isDisplay) {
            String message = driver.findElement(By.id("error")).getText();
            Allure.step("Error message: " + message);
        } else {
            Allure.step("Error message is not displayed, test failed");
            Assert.fail("Error message is not displayed");
        }

    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
        if (driver != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                Allure.step("Fail screenshot", Status.FAILED);
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Allure.attachment("Fail-screenshot", new FileInputStream(src));
            }
            driver.quit();
        }
    }
}
