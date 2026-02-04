package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class AmazonTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void openAmazonAndSearchItem() {

        String searchTerm = "laptop";

        // 1. Open Amazon
        driver.get("https://www.amazon.in");

        // 2. Enter search text
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox"))
        );
        searchBox.sendKeys(searchTerm);

        // 3. Click search
        driver.findElement(By.id("nav-search-submit-button")).click();

        // 4. Wait for search results page
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.s-main-slot")
        ));

        // 5. Get first NON-sponsored result title
        WebElement firstResult = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//div[@data-component-type='s-search-result']//h2//span)[1]")
                )
        );

        String firstResultText = firstResult.getText();

        System.out.println("Search term: " + searchTerm);
        System.out.println("First result text: " + firstResultText);

        // 6. Assertion
        Assert.assertTrue(
                firstResultText.toLowerCase().contains(searchTerm.toLowerCase()),
                "First result does not match search term!"
        );
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
