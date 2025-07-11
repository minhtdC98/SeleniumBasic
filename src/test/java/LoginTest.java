import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class LoginTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void testLogin() {
        driver.get("https://practicetestautomation.com/practice-test-login/");
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("student");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Password123");
        driver.findElement(By.xpath("//button[@id='submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Log out1']")).isDisplayed(), "Log out button is not displayed");
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public byte[] saveScreenshot(byte[] screenshot) {
        return screenshot;
    }

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                byte[] fileContent = Files.readAllBytes(srcFile.toPath());
                saveScreenshot(fileContent);
                Path destPath = Path.of("screenshots", result.getName() + ".png");
                Files.createDirectories(destPath.getParent());
                Files.copy(srcFile.toPath(), destPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
