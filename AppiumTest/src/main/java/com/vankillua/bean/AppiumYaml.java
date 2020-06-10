package com.vankillua.bean;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author KILLUA
 * @Date 2020/6/4 23:38
 * @Description
 */
@Component
@PropertySource(value = "classpath:appium-spring.yaml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "appium")
public class AppiumYaml {
    private String url;
    private Map<String, Object> capabilities;
    private Map<String, Long> wait;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Map<String, Object> capabilities) {
        this.capabilities = capabilities;
    }

    public Map<String, Long> getWait() {
        return wait;
    }

    public void setWait(Map<String, Long> wait) {
        this.wait = wait;
    }
}
