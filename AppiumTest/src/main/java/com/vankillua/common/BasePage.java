package com.vankillua.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author KILLUA
 * @Date 2020/6/4 12:52
 * @Description
 */
public class BasePage {
    protected AppiumDriver<MobileElement> driver;
    protected WebDriverWait wait;
    private static String driverType;
    private static URL url;
    private static DesiredCapabilities capabilities;
    private static long timeout;
    private static long sleepTime;
    private static long implicitlyWait;

    private static final String DEFAULT_URL = "http://127.0.0.1:4723/wd/hub";
    private static final String IOS_PLATFORM = "iOS";
    private static final long DEFAULT_TIMEOUT = 10L;
    private static final long DEFAULT_SLEEP_TIME = 1000L;

    static {
        try {
            loadYaml();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BasePage() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        driver = SingletonDriver.getInstance(driverType, url, capabilities);
        if (0 < implicitlyWait) {
            driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
        }
        if (0 < timeout && 0 < sleepTime) {
            wait = new WebDriverWait(driver, timeout, sleepTime);
        }
    }

    private static void loadYaml() throws IOException {
        Yaml yaml = new Yaml();
        InputStream is = BasePage.class.getClassLoader().getResourceAsStream("appium.yaml");
        if (Optional.ofNullable(is).isPresent()) {
            AppiumYaml appiumYaml = yaml.loadAs(is, AppiumYaml.class);
            is.close();
            url = new URL(appiumYaml.getUrl().isEmpty() ? DEFAULT_URL : appiumYaml.getUrl());
            capabilities = new DesiredCapabilities();
            appiumYaml.getCapabilities().forEach((k, v) -> capabilities.setCapability(k, v));
            String platformName = (String) appiumYaml.getCapabilities().getOrDefault(CapabilityType.PLATFORM_NAME, "");
            if (IOS_PLATFORM.equalsIgnoreCase(platformName)) {
                driverType = "io.appium.java_client.ios.IOSDriver";
            } else {
                driverType = "io.appium.java_client.android.AndroidDriver";
            }
            timeout = appiumYaml.getWait().getOrDefault("timeout", DEFAULT_TIMEOUT);
            sleepTime = appiumYaml.getWait().getOrDefault("sleepTime", DEFAULT_SLEEP_TIME);
            implicitlyWait = appiumYaml.getWait().getOrDefault("implicitlyWait", 0L);
        } else {
            throw new NoSuchFileException("appium.yaml");
        }
    }

    public static By getXpath(Object o) {
        return o instanceof By ? (By) o : By.xpath((String) o);
    }

    public void click(Object object) {
        if (object instanceof WebElement) {
            wait.until(ExpectedConditions.elementToBeClickable((WebElement) object)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(getXpath(object))).click();
        }
        // 检查页面是否弹出"下次再说 - 立即评价"弹框
//        try {
//            WebElement element = new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@resource-id=\"com.xueqiu.android:id/tv_left\" and @text=\"下次再说\"]")));
//            element.click();
//        } catch (TimeoutException ignored) {
//        }
    }

    public void clearAndSendKeys(By by, CharSequence... keysToSend) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.clear();
        element.sendKeys(keysToSend);
    }

    public void sendKeys(By by, CharSequence... keysToSend) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by)).sendKeys(keysToSend);
    }

    public boolean isExists(Object object) {
        try {
            return !wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getXpath(object))).isEmpty();
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isExists(Object object, long timeoutInSeconds) {
        try {
            if (0 == timeoutInSeconds) {
                return !driver.findElements(getXpath(object)).isEmpty();
            } else {
                return !new WebDriverWait(driver, timeoutInSeconds)
                        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getXpath(object)))
                        .isEmpty();
            }
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isExists(WebElement element, Object object) {
        try {
            return !wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, getXpath(object))).isEmpty();
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isExists(WebElement element, Object object, long timeoutInSeconds) {
        try {
            if (0 == timeoutInSeconds) {
                return !driver.findElements(getXpath(object)).isEmpty();
            } else {
                return !new WebDriverWait(driver, timeoutInSeconds)
                        .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, getXpath(object)))
                        .isEmpty();
            }
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public void quit() {
        driver.quit();
    }
}
