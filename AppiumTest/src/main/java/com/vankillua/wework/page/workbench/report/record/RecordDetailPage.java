package com.vankillua.wework.page.workbench.report.record;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.workbench.report.record.RecordDetailLocation;
import com.vankillua.wework.page.workbench.report.RecordReportPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/13 18:48
 * @Description
 */
@Component
public class RecordDetailPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(RecordDetailPage.class);

    private BasePage prePage;

    private RecordDetailLocation recordDetailLocation;

    @Autowired
    void setRecordDetailLocation(RecordDetailLocation recordDetailLocation) {
        this.recordDetailLocation = recordDetailLocation;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecordDetailPage waitForPage() {
        if (!isExists(recordDetailLocation.getMoreButton())) {
            logger.warn("等待超时，汇报 -> 记录 -> 详情页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecordDetailPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    public RecordReportPage deleteRecord() {
        // 点击更多按钮
        click(recordDetailLocation.getMoreButton());
        // 点击删除按钮
        click(recordDetailLocation.getDeleteButton());
        // 点击确定按钮
        click(recordDetailLocation.getConfirmButton());
        // 等待跳转回记录页
        return ((RecordReportPage) prePage).waitForPage();
    }

    public RecordReportPage backToRecordPage() {
        click(recordDetailLocation.getBackButton());
        return ((RecordReportPage) prePage).waitForPage();
    }
}
