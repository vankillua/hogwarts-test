package com.vankillua.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Capabilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

/**
 * @Author KILLUA
 * @Date 2020/6/4 12:57
 * @Description
 */
public class SingletonDriver {
    private static AppiumDriver<MobileElement> singletonDriver = null;

    private SingletonDriver() {}

    public static AppiumDriver<MobileElement> getInstance(String driverType, URL remoteAddress, Capabilities desiredCapabilities) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        if (null == singletonDriver) {
            synchronized (SingletonDriver.class) {
                if (null == singletonDriver) {
                    Class[] parameters = {URL.class, Capabilities.class};
                    Constructor<AppiumDriver<MobileElement>> constructor = (Constructor<AppiumDriver<MobileElement>>) Class.forName(driverType).getConstructor(parameters);
                    singletonDriver = constructor.newInstance(remoteAddress, desiredCapabilities);
                }
            }
        }
        return singletonDriver;
    }
}
