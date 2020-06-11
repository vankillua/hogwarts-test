package com.vankillua.wework.bean;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/11 15:03
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/report-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "report")
public class ReportPageLocation {
    private String backButton;
    private String dailyButton;

    public String getBackButton() {
        return backButton;
    }

    public void setBackButton(String backButton) {
        this.backButton = backButton;
    }

    public String getDailyButton() {
        return dailyButton;
    }

    public void setDailyButton(String dailyButton) {
        this.dailyButton = dailyButton;
    }
}
