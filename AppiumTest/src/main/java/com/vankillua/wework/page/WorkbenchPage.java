package com.vankillua.wework.page;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.BottomMenuLocation;
import com.vankillua.wework.bean.WorkbenchLocation;
import com.vankillua.wework.page.workbench.ReportPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/11 10:15
 * @Description
 */
@Component
public class WorkbenchPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(WorkbenchPage.class);

    private BasePage prePage;

    private WorkbenchLocation workbenchLocation;

    private BottomMenuLocation bottomMenuLocation;

    private ReportPage reportPage;

    private MainPage mainPage;

    @Autowired
    void setWorkbenchLocation(WorkbenchLocation workbenchLocation) {
        this.workbenchLocation = workbenchLocation;
    }

    @Autowired
    void setBottomMenuLocation(BottomMenuLocation bottomMenuLocation) {
        this.bottomMenuLocation = bottomMenuLocation;
    }

    @Autowired
    void setReportPage(ReportPage reportPage) {
        this.reportPage = reportPage;
    }

    @Autowired
    void setMainPage(MainPage mainPage) {
        this.mainPage = mainPage;
    }

    @Override
    @SuppressWarnings("unchecked")
    public WorkbenchPage waitForPage() {
        int times = WAIT_TIMES;
        do {
            if (isExists(workbenchLocation.getManageButton())) {
                break;
            }
        } while (0 < --times);
        if (0 == times) {
            logger.warn("等待超时，工作台页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public WorkbenchPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    public ReportPage toReportPage() {
        scrollToUi(workbenchLocation.getReportButton()).click();
        return reportPage.waitForPage().setPrePage(this);
    }

    public MainPage backToMainPage() {
        click(bottomMenuLocation.getMessageMenu());
        return mainPage.waitForPage().setPrePage(this);
    }
}
