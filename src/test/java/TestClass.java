import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestClass {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void test() {
        Allure.step("Go to page");
        driver.get("https://practicetestautomation.com/practice-test-login/");
        driver.findElement(By.xpath("//input[@id=\"username\"]")).sendKeys("student");
        driver.findElement(By.xpath("//input[@id=\"password\"]")).sendKeys("Password123");
        driver.findElement(By.xpath("//button[@id=\"submit\"]")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()=\"Log out1\"]")).isDisplayed(), "Log out button is not displayed");
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
