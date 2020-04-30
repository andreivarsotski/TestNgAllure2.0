package by.company.app.driver;

import by.company.app.exception.DriverException;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

public class DriverManager implements IDriver {

    private final String pathToServer = "http://127.0.0.1:4444/wd/hub";

    private final String enableVNC = "enableVNC";
    private final String enableVideo = "enableVideo";

    @Override
    public void start(Browser browser, Environment evn) {
        WebDriverRunner.setWebDriver(getInstance(browser, evn));
    }

    @Override
    public void quit() {
        WebDriverRunner.getWebDriver().quit();
    }

    private WebDriver getInstance(Browser browser, Environment evn) {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser.getName());
        capabilities.setVersion(browser.getVersion());
        capabilities.setCapability(enableVNC, true);
        capabilities.setCapability(enableVideo, false);

        switch (evn) {

            case SELENOID:
                try {
                    return new RemoteWebDriver(URI.create(pathToServer).toURL(), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    throw new DriverException();
                }
            default:
                throw new DriverException("Environment's properties [" + evn + "] have not implemented.");

        }
    }
}
