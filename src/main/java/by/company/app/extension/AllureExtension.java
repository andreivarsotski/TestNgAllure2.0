package by.company.app.extension;

import by.company.app.appender.AllureAppender;
import by.company.app.util.GenerateUtil;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AllureExtension extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult iTestResult) {

        if (!iTestResult.getThrowable().getMessage().contains("Screenshot"))
            Screenshots.takeScreenShot(GenerateUtil.getUniqueNum());

        attachToAllure(AllureAppender.reset());
        attachAllureImg(Screenshots.getLastScreenshot());
        attachAllureHtmlLink(Screenshots.getLastScreenshot());
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        attachToAllure(AllureAppender.reset());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        attachToAllure(AllureAppender.reset());
    }

    @Attachment(value = "Log", type = "text/plain")
    public String attachToAllure(String log) {
        return log;
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachAllureImg(File lastScreenshot) {
        try {
            return Files.readAllBytes(Paths.get(lastScreenshot.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
            String msg = "Ошибка формирования скриншота для allure";
            return msg.getBytes();
        }
    }

    @Attachment(value = "PageSource", type = "text/plain")
    private String attachAllureHtmlLink(File screenshot) {

        String pathToHtml = screenshot.getPath().replace("png", "html");

        File htmlFile = new File(pathToHtml);

        try {
            if (htmlFile.exists())
                return Paths.get(htmlFile.toURI()).toUri().toString();

            else if (WebDriverRunner.getWebDriver() != null) {

                htmlFile = new File("build/reports/tests/tempPageSource" + Thread.currentThread().getId() + ".html");

                try {
                    FileUtils.writeStringToFile(htmlFile, WebDriverRunner.getWebDriver().getPageSource(), StandardCharsets.UTF_8);
                    return Paths.get(htmlFile.toURI()).toUri().toString();
                } finally {
                    if (htmlFile.exists())
                        htmlFile.delete();
                }

            } else return "Не удалось добавить исходный код страницы";

        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при добавлении исходного кода старницы";
        }
    }

}
