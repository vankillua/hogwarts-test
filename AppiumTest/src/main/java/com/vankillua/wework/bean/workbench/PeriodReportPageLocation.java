package com.vankillua.wework.bean.workbench;

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
@ConfigurationProperties(prefix = "report.create.daily")
public class PeriodReportPageLocation {
    private String backButton;
    private String jobInput;
    private String planInput;
    private String submitButton;
    private String confirmButton;

    public String getBackButton() {
        return backButton;
    }

    public void setBackButton(String backButton) {
        this.backButton = backButton;
    }

    public String getJobInput() {
        return jobInput;
    }

    public void setJobInput(String jobInput) {
        this.jobInput = jobInput;
    }

    public String getPlanInput() {
        return planInput;
    }

    public void setPlanInput(String planInput) {
        this.planInput = planInput;
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
