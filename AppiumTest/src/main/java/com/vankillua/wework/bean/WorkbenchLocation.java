package com.vankillua.wework.bean;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/11 11:57
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/workbench-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "workbench")
public class WorkbenchLocation {
    private String manageButton;
    private String reportButton;

    public String getManageButton() {
        return manageButton;
    }

    public void setManageButton(String manageButton) {
        this.manageButton = manageButton;
    }

    public String getReportButton() {
        return reportButton;
    }

    public void setReportButton(String reportButton) {
        this.reportButton = reportButton;
    }
}
