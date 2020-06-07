package com.vankillua.wework.page;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author KILLUA
 * @Date 2020/5/31 13:27
 * @Description
 */
public class BasePage {
//    private static BasePage basePage;
    protected RemoteWebDriver driver;
    protected WebDriverWait wait;
    private static ObjectMapper mapper = new ObjectMapper();

    private static final String DEFAULT_BROWSER = "Chrome";
    private static final String CHROME_BROWSER = DEFAULT_BROWSER;
    private static final String FIREFOX_BROWSER = "FireFox";
    private static final long DEFAULT_TIMEOUT = 10L;
    private static final long DEFAULT_SLEEP_TIME = 1000L;

    public BasePage() {
        String browser = (null == System.getenv("browser")) ? DEFAULT_BROWSER : System.getenv("browser");
        long timeout = (null == System.getenv("wait.timeout")) ? DEFAULT_TIMEOUT : Long.parseLong(System.getenv("wait.timeout"));
        long sleep = (null == System.getenv("wait.sleep")) ? DEFAULT_SLEEP_TIME : Long.parseLong(System.getenv("wait.sleep"));
        init(browser, timeout, sleep);
    }

    public BasePage(RemoteWebDriver driver) {
        this.driver = driver;
        long timeout = (null == System.getenv("wait.timeout")) ? DEFAULT_TIMEOUT : Long.parseLong(System.getenv("wait.timeout"));
        long sleep = (null == System.getenv("wait.sleep")) ? DEFAULT_SLEEP_TIME : Long.parseLong(System.getenv("wait.sleep"));
        init(null, timeout, sleep);
    }

    public BasePage(RemoteWebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

//    public static BasePage getInstance() {
//        if (null == basePage) {
//            synchronized (BasePage.class) {
//                if (null == basePage) {
//                    basePage = new BasePage();
//                    String browser = System.getenv("browser");
//                    init(null == browser ? DEFAULT_BROWSER : browser);
//                }
//            }
//        }
//        return basePage;
//    }

    private void init(String browser, long timeout, long sleep) {
        if (null != browser) {
            if (FIREFOX_BROWSER.equalsIgnoreCase(browser)) {
                driver = new FirefoxDriver();
            } else {
                driver = new ChromeDriver();
            }
            driver.manage().window().maximize();
        }
        if (0 < timeout && 0 < sleep) {
            wait = new WebDriverWait(driver, timeout, sleep);
        }
    }

    protected void loadCookies() throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream("cookies.json");
        if (Optional.ofNullable(is).isPresent()) {
            List<Map<String, Object>> list = mapper.readValue(is, new TypeReference<List<Map<String, Object>>>() { });
            list.forEach(m -> driver.manage().addCookie(toCookie(m)));
            is.close();
        }
    }

    private static Cookie toCookie(Map<String, Object> m) {
        Date expiry = null;
        if (null != m.getOrDefault("expiry", null)) {
            expiry = new Date(Long.parseLong(String.valueOf(m.get("expiry"))));
        }
        return new Cookie(
                String.valueOf(m.getOrDefault("name", "")),
                String.valueOf(m.getOrDefault("value", null)),
                String.valueOf(m.getOrDefault("domain", null)),
                String.valueOf(m.getOrDefault("path", null)),
                expiry,
                Boolean.parseBoolean(String.valueOf(m.getOrDefault("secure", false))),
                Boolean.parseBoolean(String.valueOf(m.getOrDefault("httpOnly", false))));
    }

    public void get(String url) {
        driver.get(url);
    }

    public static By getSelector(Object o) {
        return o instanceof By ? (By) o : By.cssSelector((String) o);
    }

    public void click(Object object) {
        if (object instanceof WebElement) {
            wait.until(ExpectedConditions.elementToBeClickable((WebElement) object)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(getSelector(object))).click();
        }
    }

    public void jsClick(Object object) {
        WebElement element;
        if (!(object instanceof WebElement)) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(getSelector(object)));
        } else {
            element = (WebElement) object;
        }
        driver.executeScript("arguments[0].click();", element);
    }

    public void moveToClick(Object object) {
        WebElement element;
        if (!(object instanceof WebElement)) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(getSelector(object)));
        } else {
            element = (WebElement) object;
        }
        moveToClick(element, 0, 0);
    }

    public void moveToClick(Object object, int offsetX, int offsetY) {
        WebElement element;
        if (!(object instanceof WebElement)) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(getSelector(object)));
        } else {
            element = (WebElement) object;
        }
        new Actions(driver).moveToElement(element, offsetX, offsetY).click().build().perform();
    }

    public void moveToJsClick(Object object) {
        WebElement element;
        if (!(object instanceof WebElement)) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(getSelector(object)));
        } else {
            element = (WebElement) object;
        }
        moveToJsClick(element, 0, 0);
    }

    public void moveToJsClick(Object object, int offsetX, int offsetY) {
        WebElement element;
        if (!(object instanceof WebElement)) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(getSelector(object)));
        } else {
            element = (WebElement) object;
        }
        driver.executeScript("var rect = arguments[0].getBoundingClientRect();" +
                        "arguments[0].dispatchEvent(new MouseEvent('click', {" +
                        " 'view': window," +
                        " 'bubbles': true," +
                        " 'cancelable': true," +
                        " 'clientX': rect.left + rect.width/2 + arguments[1]," +
                        " 'clientY': rect.top + rect.height/2 + arguments[2]" +
                        "}))",
                element,
                offsetX,
                offsetY);
    }

    public void sendKeys(By by, CharSequence... keysToSend) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by)).sendKeys(keysToSend);
    }

    public void scrollTo(WebElement locator) {
        Point point = locator.getLocation();
        driver.executeScript("window.scrollTo(" + point.getX() + ", " + point.getY() + ")");
    }

    public void scrollIntoView(WebElement locator, Boolean args) {
        String param = Optional.ofNullable(args).isPresent() ? args.toString() : "";
        driver.executeScript("arguments[0].scrollIntoView(" + param + ")", locator);
    }

    public void hover(WebElement locator) {
        new Actions(driver).moveToElement(locator).perform();
    }

    public void elementSnapshot(WebElement locator, File output) throws IOException {
        // 创建全屏截图
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage image = ImageIO.read(screen);

        // 创建一个矩形使用上面的高度，和宽度
        Rectangle rect = new Rectangle(locator.getLocation(), locator.getSize());
        BufferedImage subImage = image.getSubimage(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        ImageIO.write(subImage, "png", output);
    }

    public WebElement getCurrentFrame() {
        return driver.findElement(By.cssSelector("nav.frame_nav>.frame_nav_item_Curr"));
    }

    public void quit() {
        driver.quit();
    }
}
