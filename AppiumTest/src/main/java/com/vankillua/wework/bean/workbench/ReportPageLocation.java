package com.vankillua.wework.bean.workbench;

import com.vankillua.common.CommonPropertyResourceFactory;
import com.vankillua.wework.bean.workbench.report.CreateReportLocation;
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
    private String createMenu;
    private String recordMenu;
    private String statisticMenu;
    private String templateMenu;

    private CreateReportLocation create;

    public String getBackButton() {
        return backButton;
    }

    public void setBackButton(String backButton) {
        this.backButton = backButton;
    }

    public String getCreateMenu() {
        return createMenu;
    }

    public void setCreateMenu(String createMenu) {
        this.createMenu = createMenu;
    }

    public String getRecordMenu() {
        return recordMenu;
    }

    public void setRecordMenu(String recordMenu) {
        this.recordMenu = recordMenu;
    }

    public String getStatisticMenu() {
        return statisticMenu;
    }

    public void setStatisticMenu(String statisticMenu) {
        this.statisticMenu = statisticMenu;
    }

    public String getTemplateMenu() {
        return templateMenu;
    }

    public void setTemplateMenu(String templateMenu) {
        this.templateMenu = templateMenu;
    }

    public CreateReportLocation getCreate() {
        return create;
    }

    public void setCreate(CreateReportLocation create) {
        this.create = create;
    }
}
