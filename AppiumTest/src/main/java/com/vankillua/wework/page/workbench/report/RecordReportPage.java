package com.vankillua.wework.page.workbench.report;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.workbench.ReportPageLocation;
import com.vankillua.wework.bean.workbench.report.RecordReportLocation;
import com.vankillua.wework.page.WorkbenchPage;
import com.vankillua.wework.page.workbench.ReportPage;
import com.vankillua.wework.page.workbench.report.record.RecordDetailPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author KILLUA
 * @Date 2020/6/12 11:33
 * @Description
 *
 * 企业微信汇报 - 记录页
 */
@Component
public class RecordReportPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(RecordReportPage.class);

    private BasePage prePage;

    private ReportPageLocation reportPageLocation;

    private RecordReportLocation recordReportLocation;

    private ReportPage reportPage;

    private RecordDetailPage recordDetailPage;

    @Autowired
    void setReportPageLocation(ReportPageLocation reportPageLocation) {
        this.reportPageLocation = reportPageLocation;
    }

    @Autowired
    void setRecordReportLocation(RecordReportLocation recordReportLocation) {
        this.recordReportLocation = recordReportLocation;
    }

    @Autowired
    void setReportPage(ReportPage reportPage) {
        this.reportPage = reportPage;
    }

    @Autowired
    void setRecordDetailPage(RecordDetailPage recordDetailPage) {
        this.recordDetailPage = recordDetailPage;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecordReportPage waitForPage() {
        if (!isExists(recordReportLocation.getReceiveTab())) {
            logger.warn("等待超时，汇报 -> 记录页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecordReportPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    List<MobileElement> getMySubmitRecords() {
        // 点击“我的提交”tab页
        click(recordReportLocation.getSubmitTab());
        return finds(recordReportLocation.getSubmit().getRecordList(), 2);
    }

    /**
     * 获取我的提交记录的内容（记录页显示的内容，非详情页）
     * @param template 筛选的模板
     * @return 二维数组，每条记录作为一个数组保存其对应的内容
     */
    public List<List<String>> getMySubmitRecordsContent(String template) {
        // 点击“我的提交”tab页
        click(recordReportLocation.getSubmitTab());
        setFilterTemplate(template);
        return getMySubmitRecords()
                .stream()
                .map(e -> finds(e, recordReportLocation.getSubmit().getRecordContent(), 2))
                .map(l -> l.stream().filter(Objects::nonNull).map(MobileElement::getText).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * 获取我的提交记录的内容（记录页显示的内容，非详情页）
     * @deprecated 不推荐使用此方法，推荐使用返回二维数组的方法
     * @param template 筛选的模板
     * @return 一维数组，各条记录中的内容合并到一个数组
     */
    @Deprecated
    public List<String> getMySubmitRecordContents(String template) {
        // 点击“我的提交”tab页
        click(recordReportLocation.getSubmitTab());
        setFilterTemplate(template);
        return getMySubmitRecords()
                .stream()
                .map(e -> finds(e, recordReportLocation.getSubmit().getRecordContent(), 2))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(MobileElement::getText)
                .collect(Collectors.toList());
    }

    private void setFilterTemplate(String template) {
        RecordReportLocation.SubmitRecordLocation submit = recordReportLocation.getSubmit();
        template = Optional.ofNullable(template).isPresent() ? template : submit.getTemplateOptions().get(0);
        if (isNotExists(submit.getTemplateSelect(), 0)) {
            logger.warn("模板选取组件不存在，无法设置模板");
            return;
        }
        String currentTemplate = Objects.requireNonNull(find(submit.getTemplateSelect())).getText();
        // 当前模板不是目标模板，则需要选择模板
        if (!template.equals(currentTemplate)) {
            int index = submit.getTemplateOptions().indexOf(template);
            int currentIndex = submit.getTemplateOptions().indexOf(currentTemplate);
            selectTemplate(Math.abs(index - currentIndex), index > currentIndex);
            currentTemplate = Objects.requireNonNull(find(submit.getTemplateSelect())).getText();
            if (!template.equals(currentTemplate)) {
                logger.warn("选择模板后，当前显示的模板【{}】仍然不是目标模板【{}】", currentTemplate, template);
            }
        }
    }

    private void selectTemplate(int times, boolean isUp) {
        if (0 < times) {
            RecordReportLocation.SubmitRecordLocation submit = recordReportLocation.getSubmit();
            // 点击模板选择器
            if (isNotExists(submit.getTemplateSelect(), 0)) {
                logger.warn("模板选取组件不存在，无法选择目标模板");
                return;
            }
            click(submit.getTemplateSelect());
            MobileElement element = find(submit.getTemplateSelector());
            if (null == element) {
                logger.warn("模板选择器没有找到，无法选择目标模板");
                return;
            }
            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            int x = element.getLocation().getX();
            int y = element.getLocation().getY();
            int startX = x + width / 2;
            int startY = y + height / 2;
            int endX = startX;
            // 模板选择器一共可以显示5个选项，每次滑动一个选项
            int endY = isUp ? startY - height / 5 : startY + height / 5;
            TouchAction touchAction = new TouchAction(driver);
            while (0 < times--) {
                touchAction.press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                        .moveTo(PointOption.point(endX, endY))
                        .release().perform();
            }
            click(submit.getTemplateConfirmButton());

        }
    }

    public RecordReportPage deleteMySubmitRecords(String template) {
        // 点击“我的提交”tab页
        click(recordReportLocation.getSubmitTab());
        setFilterTemplate(template);
        do {
            List<MobileElement> elements = getMySubmitRecords();
            if (elements.isEmpty()) {
                break;
            } else {
                toRecordDetailPage(elements.get(0)).deleteRecord();
            }
            // 记录详情页删除记录后，跳转回记录页时，获取记录列表有可能取到刚删除的记录，导致抛出StaleElementReferenceException
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
                logger.error("强制等待时遇到异常：", ignored);
            }
        } while (true);
        return this;
    }

    public RecordDetailPage toRecordDetailPage(Object object) {
        click(object);
        return recordDetailPage.waitForPage().setPrePage(this);
    }

    public ReportPage toCreatePage() {
        click(reportPageLocation.getCreateMenu());
        return reportPage.waitForPage().setPrePage(prePage);
    }

    public WorkbenchPage backToWorkbenchPage() {
        click(reportPageLocation.getBackButton());
        return ((WorkbenchPage) prePage).waitForPage();
    }
}
