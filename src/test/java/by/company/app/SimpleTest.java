package by.company.app;

import by.company.app.driver.Browser;
import by.company.app.driver.DriverManager;
import by.company.app.driver.Environment;
import com.codeborne.selenide.Condition;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SimpleTest {

    private DriverManager dm = new DriverManager();

    private final String path = "https://www.google.com/";
    private final String searchInput = "[id='tophf'] ~ div input";

    @BeforeMethod(alwaysRun = true)
    void start() {
        dm.start(Browser.CHROME_80, Environment.SELENOID);
    }

    @Test
    void checkOutAllureReport() {
        open(path);
        $(searchInput).shouldBe(Condition.visible);
    }

    @AfterMethod(alwaysRun = true)
    void tearDown() {
        dm.quit();
    }

}
