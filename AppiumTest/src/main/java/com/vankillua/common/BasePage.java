package com.vankillua.common;

import com.vankillua.bean.AppiumYaml;
import com.vankillua.utils.AppiumExpectedConditions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author KILLUA
 * @Date 2020/6/4 12:52
 * @Description
 */
@Component
public abstract class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    protected AppiumDriver<MobileElement> driver;
    protected AppiumFluentWait<AppiumDriver<MobileElement>> wait;

    private static String driverType;
    private static AppiumYaml appiumYaml;

    private static URL url;
    private static DesiredCapabilities capabilities;
    private static long timeout;
    private static long sleepTime;
    private static long implicitlyWait;

    private static final String DEFAULT_URL = "http://127.0.0.1:4723/wd/hub";
    private static final String IOS_PLATFORM = "iOS";
    private static final long DEFAULT_TIMEOUT = 10L;
    private static final long DEFAULT_SLEEP_TIME = 1000L;

    protected static final int WAIT_TIMES = 3;
    protected static final String UI_SELECTOR = "new UiSelector().";
    protected static final String UI_SCROLLABLE = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(%s)";

    @Autowired
    public void setAppiumYaml(AppiumYaml appiumYaml) {
        BasePage.appiumYaml = appiumYaml;
    }

    @PostConstruct
    void init() {
        try {
            loadYamlWithSpring();
        } catch (MalformedURLException e) {
            logger.error("加载Appium配置文件时遇到异常：", e);
        }
        driver = SingletonDriver.getInstance(driverType, url, capabilities);
        if (0 < implicitlyWait) {
            driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
        }
        if (0 < timeout && 0 < sleepTime) {
            wait = new AppiumFluentWait<>(driver);
            wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofMillis(sleepTime));
        }
    }

    @Deprecated
    private static void loadYaml() throws IOException {
        Yaml yaml = new Yaml();
        InputStream is = BasePage.class.getClassLoader().getResourceAsStream("appium.yml");
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
            throw new NoSuchFileException("appium.yml");
        }
    }

    private static void loadYamlWithSpring() throws MalformedURLException {
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
    }

    /**
     * 每个PO需要实现一个方法，用于切换到改页面时等待加载完成
     * @return 继承BasePage的泛型，用于返回当前PO
     */
    protected abstract <T extends BasePage> T waitForPage();

    /**
     * 每个PO可以根据需要实现一个方法，用于记录切换页面前的页面，便于后面返回切换前的页面
     * @param currentPage 切换页面前的PO
     * @return 切换页面后的PO
     */
    protected abstract <T extends BasePage> T setPrePage(T currentPage);

    public static By getXpath(Object o) {
        return o instanceof By ? (By) o : By.xpath((String) o);
    }

    public static String getUIAutomator(String s) {
        return s.startsWith(UI_SELECTOR) ? s : UI_SELECTOR + s;
    }

    public void click(Object object) {
        if (object instanceof WebElement) {
            wait.until(ExpectedConditions.visibilityOf((WebElement) object)).click();
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(getXpath(object))).click();
        }
        // 检查页面是否弹出"下次再说 - 立即评价"弹框
//        try {
//            WebElement element = new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@resource-id=\"com.xueqiu.android:id/tv_left\" and @text=\"下次再说\"]")));
//            element.click();
//        } catch (TimeoutException ignored) {
//        }
    }

    public void sendKeys(Object object, CharSequence... keysToSend) {
        if (object instanceof WebElement) {
            ((WebElement) object).sendKeys(keysToSend);
        } else {
            wait.until(ExpectedConditions.presenceOfElementLocated(getXpath(object))).sendKeys(keysToSend);
        }
    }

    public void sendKeys(Object object, boolean needToClear, CharSequence... keysToSend) {
        WebElement element;
        if (object instanceof WebElement) {
            element = (WebElement) object;
        } else {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(getXpath(object)));
        }
        if (needToClear) {
            element.clear();
        }
        element.sendKeys(keysToSend);
    }

    public MobileElement find(Object object) {
        try {
            return wait.until(AppiumExpectedConditions.presenceOfElementLocated(getXpath(object)));
        } catch (TimeoutException ignored) {
            return null;
        }
    }

    public MobileElement find(Object object, long timeoutInSeconds) {
        try {
            if (0 >= timeoutInSeconds) {
                return driver.findElement(getXpath(object));
            } else {
                return new AppiumFluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                        .until(AppiumExpectedConditions.presenceOfElementLocated(getXpath(object)));
            }
        } catch (TimeoutException ignored) {
            return null;
        }
    }

    public MobileElement find(MobileElement element, Object object) {
        try {
            return wait.until(AppiumExpectedConditions.presenceOfNestedElementLocated(element, getXpath(object)));
        } catch (TimeoutException ignored) {
            return null;
        }
    }

    public MobileElement find(MobileElement element, Object object, long timeoutInSeconds) {
        try {
            if (0 >= timeoutInSeconds) {
                return element.findElement(getXpath(object));
            } else {
                return new AppiumFluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                        .until(AppiumExpectedConditions.presenceOfNestedElementLocated(element, getXpath(object)));
            }
        } catch (TimeoutException ignored) {
            return null;
        }
    }

    public List<MobileElement> finds(Object object) {
        try {
            return wait.until(AppiumExpectedConditions.presenceOfAllElementsLocatedBy(getXpath(object)));
        } catch (TimeoutException ignored) {
            return Collections.emptyList();
        }
    }

    public List<MobileElement> finds(Object object, long timeoutInSeconds) {
        try {
            if (0 >= timeoutInSeconds) {
                return driver.findElements(getXpath(object));
            } else {
                return new AppiumFluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                        .until(AppiumExpectedConditions.presenceOfAllElementsLocatedBy(getXpath(object)));
            }
        } catch (TimeoutException ignored) {
            return Collections.emptyList();
        }
    }

    public List<MobileElement> finds(MobileElement element, Object object) {
        try {
            return wait.until(AppiumExpectedConditions.presenceOfNestedElementsLocatedBy(element, getXpath(object)));
        } catch (TimeoutException ignored) {
            return Collections.emptyList();
        }
    }

    public List<MobileElement> finds(MobileElement element, Object object, long timeoutInSeconds) {
        try {
            if (0 >= timeoutInSeconds) {
                return element.findElements(getXpath(object));
            } else {
                return new AppiumFluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                        .until(AppiumExpectedConditions.presenceOfNestedElementsLocatedBy(element, getXpath(object)));
            }
        } catch (TimeoutException ignored) {
            return Collections.emptyList();
        }
    }

    public boolean isExists(Object object) {
        try {
            return !wait.until(AppiumExpectedConditions.visibilityOfAllElementsLocatedBy(getXpath(object))).isEmpty();
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isExists(Object object, long timeoutInSeconds) {
        timeoutInSeconds = (0 >= timeoutInSeconds) ? 1 : timeoutInSeconds;
        try {
            return !new AppiumFluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                    .until(AppiumExpectedConditions.visibilityOfAllElementsLocatedBy(getXpath(object)))
                    .isEmpty();
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isExists(MobileElement element, Object object) {
        try {
            return !wait.until(AppiumExpectedConditions.visibilityOfNestedElementsLocatedBy(element, getXpath(object))).isEmpty();
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isExists(MobileElement element, Object object, long timeoutInSeconds) {
        timeoutInSeconds = (0 >= timeoutInSeconds) ? 1 : timeoutInSeconds;
        try {
            return !new AppiumFluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                    .until(AppiumExpectedConditions.visibilityOfNestedElementsLocatedBy(element, getXpath(object)))
                    .isEmpty();
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isNotExists(Object object) {
        try {
            return wait.until(AppiumExpectedConditions.invisibilityOfElementLocated(getXpath(object)));
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isNotExists(Object object, long timeoutInSeconds) {
        timeoutInSeconds = (0 >= timeoutInSeconds) ? 1 : timeoutInSeconds;
        try {
            return new AppiumFluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                    .until(AppiumExpectedConditions.invisibilityOfElementLocated(getXpath(object)));
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isNotExists(MobileElement element, Object object) {
        try {
            return wait.until(AppiumExpectedConditions.invisibilityOfNestedElementLocated(element, getXpath(object)));
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean isNotExists(MobileElement element, Object object, long timeoutInSeconds) {
        timeoutInSeconds = (0 >= timeoutInSeconds) ? 1 : timeoutInSeconds;
        try {
            return new AppiumFluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                    .until(AppiumExpectedConditions.invisibilityOfNestedElementLocated(element, getXpath(object)));
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public MobileElement findUi(String ui) {
        return wait.until(AppiumExpectedConditions.presenceOfAndroidUiLocated(getUIAutomator(ui)));
    }

    public MobileElement scrollToUi(String ui) {
        String uiScrollable = String.format(UI_SCROLLABLE, getUIAutomator(ui));
        return ((AndroidDriver<MobileElement>) driver).findElementByAndroidUIAutomator(uiScrollable);
    }

    public void quit() {
        driver.quit();
    }
}
