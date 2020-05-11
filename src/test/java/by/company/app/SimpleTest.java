package by.company.app;

import by.company.app.annotation.TestCaseId;
import by.company.app.driver.Browser;
import by.company.app.driver.DriverManager;
import by.company.app.driver.Environment;
import by.company.app.extension.AllureExtension;
import by.company.app.extension.AllureTmsLinkExtension;
import com.codeborne.selenide.Condition;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Listeners({
        AllureExtension.class,
        AllureTmsLinkExtension.class
})
public class SimpleTest {

    private static final Logger logger = Logger.getLogger(SimpleTest.class);


    private DriverManager dm = new DriverManager();

    private final String path = "https://www.google.com/";
    private final String searchInput = "[id='tophf'] ~ div input";

    @BeforeMethod(alwaysRun = true)
    void start() {
        dm.start(Browser.CHROME_80, Environment.SELENOID);
    }

    @TestCaseId(1)
    @Test
    void checkOutAllureReport() {
        open(path);
        logger.info("Path is opened");
        $(searchInput).shouldBe(Condition.visible);
        Assert.fail("Let's look at screen");
    }

    @AfterMethod(alwaysRun = true)
    void tearDown() {
        dm.quit();
    }

}
