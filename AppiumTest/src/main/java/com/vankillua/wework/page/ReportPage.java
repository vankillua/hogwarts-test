package com.vankillua.wework.page;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.ReportPageLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/11 15:00
 * @Description
 *
 * 企业微信汇报页
 */
@Component
public class ReportPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ReportPage.class);

    private BasePage prePage;

    private ReportPageLocation reportPageLocation;

    private DailyReportPage dailyReportPage;

    @Autowired
    void setReportPageLocation(ReportPageLocation reportPageLocation) {
        this.reportPageLocation = reportPageLocation;
    }

    @Autowired
    void setDailyReportPage(DailyReportPage dailyReportPage) {
        this.dailyReportPage = dailyReportPage;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ReportPage waitForPage() {
        if (!isExists(reportPageLocation.getBackButton())) {
            logger.warn("等待超时，汇报页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ReportPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    ReportPage addDailyReport(String todayJob) {
        click(scrollToUi(reportPageLocation.getDailyButton()));
        try {
            dailyReportPage.addDailyReport(todayJob);
            int times = WAIT_TIMES;
            do {
                if (isExists(reportPageLocation.getBackButton())) {
                    break;
                }
            } while (0 < --times);
            if (0 == times) {
                logger.warn("等待超时，未从新建日报页返回到汇报页");
            }
        } catch (Exception ignored) {
            dailyReportPage.cancelAddDailyReport();
        }
        return this;
    }

    BasePage backToWorkbenchPage() {
        click(reportPageLocation.getBackButton());
        return prePage;
    }
}
