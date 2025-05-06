package Trail_TestCases;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Set;

public class Open_InNewWindow {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.selenium.dev/categories/releases/");
    }

    @Test
    public void openLinkInNewTabAndSwitchFocus() {
        String originalWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        // Find the link to Selenium 4.29 release
        WebElement link = driver.findElement(By.xpath("//a[@href='/blog/2025/selenium-4-29-released/']"));

        // CTRL + Click to open in new tab
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL).click(link).keyUp(Keys.CONTROL).build().perform();

        // Wait until a new window handle appears
        new WebDriverWait(driver, Duration.ofSeconds(10)).until((ExpectedCondition<Boolean>) wd -> {
            return wd.getWindowHandles().size() > oldWindows.size();
        });

        // Get the new window handle
        Set<String> newWindows = driver.getWindowHandles();
        newWindows.removeAll(oldWindows); // remove old ones, leaving only the new

        for (String newWindow : newWindows) {
            driver.switchTo().window(newWindow);
            break;
        }

        // Now we are in the new tab
        System.out.println("New tab title: " + driver.getTitle());

        // Close new tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
