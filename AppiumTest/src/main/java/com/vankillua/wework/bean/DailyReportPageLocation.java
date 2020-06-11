package com.vankillua.wework.bean;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/11 15:57
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/report-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "report.daily")
public class DailyReportPageLocation {
    private String backButton;
    private String todayJobInput;
    private String tomorrowPlanInput;
    private String submitButton;
    private String confirmButton;

    public String getBackButton() {
        return backButton;
    }

    public void setBackButton(String backButton) {
        this.backButton = backButton;
    }

    public String getTodayJobInput() {
        return todayJobInput;
    }

    public void setTodayJobInput(String todayJobInput) {
        this.todayJobInput = todayJobInput;
    }

    public String getTomorrowPlanInput() {
        return tomorrowPlanInput;
    }

    public void setTomorrowPlanInput(String tomorrowPlanInput) {
        this.tomorrowPlanInput = tomorrowPlanInput;
    }

    public String getSubmitButton() {
        return submitButton;
    }

    public void setSubmitButton(String submitButton) {
        this.submitButton = submitButton;
    }

    public String getConfirmButton() {
        return confirmButton;
    }

    public void setConfirmButton(String confirmButton) {
        this.confirmButton = confirmButton;
    }
}
