package com.vankillua.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Capabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @Author KILLUA
 * @Date 2020/6/4 12:57
 * @Description
 */
public class SingletonDriver {
    private final static Logger logger = LoggerFactory.getLogger(SingletonDriver.class);

    private static AppiumDriver<MobileElement> singletonDriver = null;

    private SingletonDriver() {}

    public static AppiumDriver<MobileElement> getInstance(String driverType, URL remoteAddress, Capabilities desiredCapabilities) {
        return getInstance(driverType, remoteAddress, desiredCapabilities, 0);
    }

    public static AppiumDriver<MobileElement> getInstance(String driverType, URL remoteAddress, Capabilities desiredCapabilities, long implicitlyWait) {
        if (null == singletonDriver) {
            synchronized (SingletonDriver.class) {
                if (null == singletonDriver) {
                    try {
                        Class[] parameters = {URL.class, Capabilities.class};
                        Constructor<AppiumDriver<MobileElement>> constructor = (Constructor<AppiumDriver<MobileElement>>) Class.forName(driverType).getConstructor(parameters);
                        singletonDriver = constructor.newInstance(remoteAddress, desiredCapabilities);
                        if (0 < implicitlyWait) {
                            singletonDriver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
                        }
                    } catch (NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        logger.error("获取AppiumDriver时遇到异常：", e);
                    }
                }
            }
        }
        return singletonDriver;
    }
}
