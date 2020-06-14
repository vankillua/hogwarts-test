package com.vankillua.wework.page.workbench;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.workbench.PeriodReportPageLocation;
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
public class PeriodReportPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(PeriodReportPage.class);

    private BasePage prePage;

    private PeriodReportPageLocation periodReportPageLocation;

    @Autowired
    void setPeriodReportPageLocation(PeriodReportPageLocation periodReportPageLocation) {
        this.periodReportPageLocation = periodReportPageLocation;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected PeriodReportPage waitForPage() {
        int times = WAIT_TIMES;
        do {
            if (isExists(periodReportPageLocation.getBackButton())) {
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
    protected PeriodReportPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    BasePage cancelAddPeriodReport() {
        click(periodReportPageLocation.getBackButton());
        return prePage;
    }

    void addPeriodReport(String job) {
        sendKeys(findUi(periodReportPageLocation.getJobInput()), true, job);
        click(scrollToUi(periodReportPageLocation.getSubmitButton()));
        // 未选择接收人，会弹框提示，需要点确定按钮
        click(periodReportPageLocation.getConfirmButton());
    }
}
