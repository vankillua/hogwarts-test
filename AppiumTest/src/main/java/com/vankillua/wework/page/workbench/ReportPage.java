package com.vankillua.wework.page.workbench;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.workbench.ReportPageLocation;
import com.vankillua.wework.page.WorkbenchPage;
import com.vankillua.wework.page.workbench.report.RecordReportPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/11 15:00
 * @Description
 *
 * 企业微信汇报 - 新建页
 */
@Component
public class ReportPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ReportPage.class);

    private BasePage prePage;

    private ReportPageLocation reportPageLocation;

    private PeriodReportPage periodReportPage;

    private RecordReportPage recordReportPage;

    @Autowired
    void setReportPageLocation(ReportPageLocation reportPageLocation) {
        this.reportPageLocation = reportPageLocation;
    }

    @Autowired
    void setPeriodReportPage(PeriodReportPage periodReportPage) {
        this.periodReportPage = periodReportPage;
    }

    @Autowired
    void setRecordReportPage(RecordReportPage recordReportPage) {
        this.recordReportPage = recordReportPage;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ReportPage waitForPage() {
        if (!isExists(reportPageLocation.getCreateMenu())) {
            logger.warn("等待超时，汇报 -> 新建页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ReportPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    private void createReport(String job) {
        try {
            periodReportPage.addPeriodReport(job);
        } catch (Exception ignored) {
            logger.error("新建周期性报告（日报、周报、月报）失败");
            periodReportPage.cancelAddPeriodReport();
        }
        // 提交报告后，会跳转到该报告的详情页面
        int times = WAIT_TIMES;
        int timeout = 5;
        do {
            if (isExists(reportPageLocation.getCreateMenu(), timeout)) {
                break;
            } else if (isExists(reportPageLocation.getBackButton(), timeout)) {
                // 从提交的详情页面返回到记录页面
                click(reportPageLocation.getBackButton());
            }
        } while (0 < --times);
        if (0 == times) {
            logger.warn("等待超时，未从新建报告页返回到汇报页");
        }
        if (isExists(reportPageLocation.getCreateMenu(), timeout)) {
            // 从记录页面返回到新建页面
            click(reportPageLocation.getCreateMenu());
        }
    }

    ReportPage addDailyReport(String todayJob) {
        // 点击“新建”菜单按钮
        click(reportPageLocation.getCreateMenu());
        // 在新建页找到“日报”并点击
        click(scrollToUi(reportPageLocation.getCreate().getDailyButton()));
        createReport(todayJob);
        return this;
    }

    ReportPage addWeeklyReport(String weekJob) {
        // 点击“新建”菜单按钮
        click(reportPageLocation.getCreateMenu());
        // 在新建页找到“周报”并点击
        click(scrollToUi(reportPageLocation.getCreate().getWeeklyButton()));
        createReport(weekJob);
        return this;
    }

    ReportPage addMonthlyReport(String monthJob) {
        // 点击“新建”菜单按钮
        click(reportPageLocation.getCreateMenu());
        // 在新建页找到“周报”并点击
        click(scrollToUi(reportPageLocation.getCreate().getMonthlyButton()));
        createReport(monthJob);
        return this;
    }

    public RecordReportPage toRecordPage() {
        click(reportPageLocation.getRecordMenu());
        return recordReportPage.waitForPage().setPrePage(prePage);
    }

    public WorkbenchPage backToWorkbenchPage() {
        click(reportPageLocation.getBackButton());
        return ((WorkbenchPage) prePage).waitForPage();
    }
}
