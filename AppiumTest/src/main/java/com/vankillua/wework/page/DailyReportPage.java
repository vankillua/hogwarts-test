package com.vankillua.wework.page;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.DailyReportPageLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/11 15:57
 * @Description
 *
 * 企业微信App日报页
 */
@Component
public class DailyReportPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(DailyReportPage.class);

    private BasePage prePage;

    private DailyReportPageLocation dailyReportPageLocation;

    @Autowired
    void setDailyReportPageLocation(DailyReportPageLocation dailyReportPageLocation) {
        this.dailyReportPageLocation = dailyReportPageLocation;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected DailyReportPage waitForPage() {
        int times = WAIT_TIMES;
        do {
            if (isExists(dailyReportPageLocation.getBackButton())) {
                break;
            }
        } while (0 < --times);
        if (0 == times) {
            logger.warn("等待超时，日报页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected DailyReportPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    BasePage cancelAddDailyReport() {
        click(dailyReportPageLocation.getBackButton());
        return prePage;
    }

    void addDailyReport(String todayJob) {
        sendKeys(findUi(dailyReportPageLocation.getTodayJobInput()), true, todayJob);
        scrollToUi(dailyReportPageLocation.getSubmitButton()).click();
        // 未选择接收人，会弹框提示，需要点确定按钮
        click(dailyReportPageLocation.getConfirmButton());
    }
}
