package com.vankillua.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.AppiumFunction;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;


/**
 * @Author KILLUA
 * @Date 2020/6/10 0:10
 * @Description
 */
public class AppiumExpectedConditions {
    private static final Logger logger = LoggerFactory.getLogger(AppiumExpectedConditions.class);

    private AppiumExpectedConditions() {}

    public static AppiumFunction<AppiumDriver<MobileElement>, MobileElement> presenceOfElementLocated(
            final By locator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, MobileElement>() {
            @NullableDecl
            @Override
            public MobileElement apply(@NullableDecl AppiumDriver<MobileElement> mobileElementAppiumDriver) {
                return Objects.requireNonNull(mobileElementAppiumDriver).findElement(locator);
            }

            @Override
            public String toString() {
                return "presence of element located by: " + locator;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, MobileElement> presenceOfNestedElementLocated(
            final MobileElement element, final By childLocator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, MobileElement>() {
            @NullableDecl
            @Override
            public MobileElement apply(@NullableDecl AppiumDriver<MobileElement> mobileElementAppiumDriver) {
                return element.findElement(childLocator);
            }

            @Override
            public String toString() {
                return String.format("presence of element located by %s", childLocator);
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>> presenceOfAllElementsLocatedBy(
            final By locator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>>() {
            @NullableDecl
            @Override
            public List<MobileElement> apply(@NullableDecl AppiumDriver<MobileElement> mobileElementAppiumDriver) {
                List<MobileElement> elements = Objects.requireNonNull(mobileElementAppiumDriver).findElements(locator);
                return elements.isEmpty() ? null : elements;
            }

            @Override
            public String toString() {
                return "presence of any elements located by " + locator;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>> presenceOfNestedElementsLocatedBy(
            final MobileElement element, final By childLocator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>>() {
            @NullableDecl
            @Override
            public List<MobileElement> apply(@NullableDecl AppiumDriver<MobileElement> mobileElementAppiumDriver) {
                List<MobileElement> allChildren = element.findElements(childLocator);
                return allChildren.isEmpty() ? null : allChildren;
            }

            @Override
            public String toString() {
                return "presence of element located by " + childLocator;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, MobileElement> visibilityOfElementLocated(
            final By locator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, MobileElement>() {
            @NullableDecl
            @Override
            public MobileElement apply(@NullableDecl AppiumDriver<MobileElement> mobileElementAppiumDriver) {
                try {
                    return elementIfVisible(Objects.requireNonNull(mobileElementAppiumDriver).findElement(locator));
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "visibility of element located by " + locator;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, MobileElement> visibilityOfNestedElementLocated(
            final MobileElement element, final By childLocator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, MobileElement>() {
            @NullableDecl
            @Override
            public MobileElement apply(@NullableDecl AppiumDriver<MobileElement> mobileElementAppiumDriver) {
                try {
                    return elementIfVisible(element.findElement(childLocator));
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "visibility of element located by " + childLocator;
            }
        };
    }

    private static MobileElement elementIfVisible(MobileElement element) {
        return element.isDisplayed() ? element : null;
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>> visibilityOfAllElementsLocatedBy(
            final By locator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>>() {
            @NullableDecl
            @Override
            public List<MobileElement> apply(@NullableDecl AppiumDriver<MobileElement> mobileElementAppiumDriver) {
                List<MobileElement> elements = Objects.requireNonNull(mobileElementAppiumDriver).findElements(locator);
                for (MobileElement element : elements) {
                    if (!element.isDisplayed()) {
                        return null;
                    }
                }
                return elements.isEmpty() ? null : elements;
            }

            @Override
            public String toString() {
                return "visibility of all elements located by " + locator;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>> visibilityOfNestedElementsLocatedBy(
            final MobileElement element, final By childLocator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>>() {
            @NullableDecl
            @Override
            public List<MobileElement> apply(@NullableDecl AppiumDriver<MobileElement> mobileElementAppiumDriver) {
                List<MobileElement> allChildren = element.findElements(childLocator);
                // The original code only checked the visibility of the first element.
                if (!allChildren.isEmpty() && allChildren.get(0).isDisplayed()) {
                    return allChildren;
                }
                return null;
            }

            @Override
            public String toString() {
                return "visibility of element located by " + childLocator;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, Boolean> invisibilityOf(final MobileElement element) {
        return new AppiumFunction<AppiumDriver<MobileElement>, Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl AppiumDriver<MobileElement> input) {
                return isInvisible(element);
            }

            @Override
            public String toString() {
                return "invisibility of " + element;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, Boolean> invisibilityOfElementLocated(
            final By locator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl AppiumDriver<MobileElement> input) {
                try {
                    return !(Objects.requireNonNull(input).findElement(locator).isDisplayed());
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                    // Returns true because the element is not present in DOM. The try block checks if the element is present but is invisible.
                    // Returns true because stale element reference implies that element is no longer visible.
                    return true;
                }
            }

            @Override
            public String toString() {
                return "element to no longer be visible: " + locator;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, Boolean> invisibilityOfNestedElementLocated(
            final MobileElement element, final By childLocator) {
        return new AppiumFunction<AppiumDriver<MobileElement>, Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl AppiumDriver<MobileElement> input) {
                try {
                    return !(element.findElement(childLocator).isDisplayed());
                } catch (NoSuchElementException | StaleElementReferenceException ignored) {
                    return true;
                }
            }

            @Override
            public String toString() {
                return "child element to no longer be visible: " + childLocator;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, Boolean> invisibilityOfAllElements(
            final List<MobileElement> elements) {
        return new AppiumFunction<AppiumDriver<MobileElement>, Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl AppiumDriver<MobileElement> input) {
                return elements.stream().allMatch(AppiumExpectedConditions::isInvisible);
            }

            @Override
            public String toString() {
                return "invisibility of all elements " + elements;
            }
        };
    }

    private static boolean isInvisible(final MobileElement element) {
        try {
            return !element.isDisplayed();
        } catch (StaleElementReferenceException ignored) {
            // We can assume a stale element isn't displayed.
            return true;
        }
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, MobileElement> presenceOfAndroidUiLocated(
            final String using) {
        return new AppiumFunction<AppiumDriver<MobileElement>, MobileElement>() {
            @NullableDecl
            @Override
            public MobileElement apply(@NullableDecl AppiumDriver<MobileElement> input) {
                return ((AndroidDriver<MobileElement>) Objects.requireNonNull(input)).findElementByAndroidUIAutomator(using);
            }

            @Override
            public String toString() {
                return "presence of android ui located by: " + using;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>> presenceOfAllAndroidUiLocated(
            final String using) {
        return new AppiumFunction<AppiumDriver<MobileElement>, List<MobileElement>>() {
            @NullableDecl
            @Override
            public List<MobileElement> apply(@NullableDecl AppiumDriver<MobileElement> input) {
                List<MobileElement> elements = ((AndroidDriver<MobileElement>) Objects.requireNonNull(input))
                        .findElementsByAndroidUIAutomator(using);
                return elements.isEmpty() ? null : elements;
            }

            @Override
            public String toString() {
                return "presence of any android ui located by " + using;
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, Boolean> presenceOfWebView() {
        return new AppiumFunction<AppiumDriver<MobileElement>, Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl AppiumDriver<MobileElement> input) {
                return Objects.requireNonNull(input).getContextHandles().stream().anyMatch(h -> h.toUpperCase().startsWith("WEBVIEW_"));
            }

            @Override
            public String toString() {
                return "presence of any webview in contexts";
            }
        };
    }

    public static AppiumFunction<AppiumDriver<MobileElement>, Boolean> webviewToBe(String contextName) {
        return new AppiumFunction<AppiumDriver<MobileElement>, Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl AppiumDriver<MobileElement> input) {
                return Objects.requireNonNull(input).getContextHandles().stream().anyMatch(h -> h.equalsIgnoreCase(contextName));
            }

            @Override
            public String toString() {
                return "presence of the webview: " + contextName;
            }
        };
    }
}
