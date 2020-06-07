package com.vankillua.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author KILLUA
 * @Date 2020/6/4 23:38
 * @Description
 */
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
