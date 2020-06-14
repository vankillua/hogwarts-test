package com.vankillua.wework.bean.workbench.report;

import com.vankillua.common.CommonPropertyResourceFactory;
import com.vankillua.wework.bean.workbench.PeriodReportPageLocation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/12 10:56
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/report-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "report.create")
public class CreateReportLocation {
    private String dailyButton;

    private String weeklyButton;

    private String monthlyButton;

    private PeriodReportPageLocation daily;

    private PeriodReportPageLocation weekly;

    private PeriodReportPageLocation monthly;

    public String getDailyButton() {
        return dailyButton;
    }

    public void setDailyButton(String dailyButton) {
        this.dailyButton = dailyButton;
    }

    public String getWeeklyButton() {
        return weeklyButton;
    }

    public void setWeeklyButton(String weeklyButton) {
        this.weeklyButton = weeklyButton;
    }

    public String getMonthlyButton() {
        return monthlyButton;
    }

    public void setMonthlyButton(String monthlyButton) {
        this.monthlyButton = monthlyButton;
    }

    public PeriodReportPageLocation getDaily() {
        return daily;
    }

    public void setDaily(PeriodReportPageLocation daily) {
        this.daily = daily;
    }

    public PeriodReportPageLocation getWeekly() {
        return weekly;
    }

    public void setWeekly(PeriodReportPageLocation weekly) {
        this.weekly = weekly;
    }

    public PeriodReportPageLocation getMonthly() {
        return monthly;
    }

    public void setMonthly(PeriodReportPageLocation monthly) {
        this.monthly = monthly;
    }
}
