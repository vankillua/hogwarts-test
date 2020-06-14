package com.vankillua.wework.page.workbench;

import com.vankillua.wework.page.MainPage;
import com.vankillua.wework.page.WorkbenchPage;
import com.vankillua.wework.page.workbench.report.RecordReportPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * @Author KILLUA
 * @Date 2020/6/11 17:03
 * @Description
 */
@SpringBootTest
@DisplayName("汇报页测试")
public class ReportPageTest {
    private static final Logger logger = LoggerFactory.getLogger(ReportPageTest.class);

    @Autowired
    private MainPage mainPage;

    private WorkbenchPage workbenchPage;

    private ReportPage reportPage;

    static MainPage staticMainPage;

    @PostConstruct
    void setStaticMainPage() {
        ReportPageTest.staticMainPage = mainPage;
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("汇报页操作 - 日报、周报、月报")
    class periodReportOperation {
        @BeforeAll
        @DisplayName("消息页 -> 工作台页")
        void beforeAll() {
            workbenchPage = mainPage.waitForPage().toWorkbenchPage();
            logger.info("从消息页进入工作台页");
        }

        @BeforeEach
        @DisplayName("工作台页 -> 汇报页")
        void beforeEach() {
            reportPage = workbenchPage.toReportPage();
            logger.info("从工作台页进入汇报页");
        }

        @Test
        @Order(1)
        @DisplayName("提交日报")
        void addDailyReport() {
            SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
            String todayJob = sdf.format(new Date()) + "工作日报";
            List<List<String>> records = reportPage.addDailyReport(todayJob).toRecordPage().getMySubmitRecordsContent("日报");
            assertThat("提交日报失败，当前我的日报提交记录数为0", records.size(), is(greaterThanOrEqualTo(1)));
            assertThat("提交日报失败，当前我的日报提交记录的第一条记录不是刚提交的日报", records.get(0).get(0), is(containsString(todayJob)));
            logger.info("提交日报【{}】成功", todayJob);
        }

        @Test
        @Order(1)
        @DisplayName("提交周报")
        void addWeeklyReport() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMWW");
            String weekJob = sdf.format(new Date()) + "工作周报";
            List<List<String>> records = reportPage.addWeeklyReport(weekJob).toRecordPage().getMySubmitRecordsContent("周报");
            assertThat("提交周报失败，当前我的周报提交记录数为0", records.size(), is(greaterThanOrEqualTo(1)));
            assertThat("提交周报失败，当前我的周报提交记录的第一条记录不是刚提交的周报", records.get(0).get(0), is(containsString(weekJob)));
            logger.info("提交周报【{}】成功", weekJob);
        }

        @Test
        @Order(1)
        @DisplayName("提交月报")
        void addMonthlyReport() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String monthJob = sdf.format(new Date()) + "工作月报";
            List<List<String>> records = reportPage.addMonthlyReport(monthJob).toRecordPage().getMySubmitRecordsContent("月报");
            assertThat("提交周报失败，当前我的月报提交记录数为0", records.size(), is(greaterThanOrEqualTo(1)));
            assertThat("提交周报失败，当前我的月报提交记录的第一条记录不是刚提交的周报", records.get(0).get(0), is(containsString(monthJob)));
            logger.info("提交周报【{}】成功", monthJob);
        }

        @ParameterizedTest(name = "删除全部{0}")
        @ValueSource(strings = {"日报", "周报", "月报"})
        @Order(2)
        @DisplayName("删除全部提交的报告")
        void deleteAllReport(String period) {
//            RecordReportPage recordReportPage = reportPage.toRecordPage();
            List<List<String>> records = reportPage.toRecordPage().deleteMySubmitRecords(period).getMySubmitRecordsContent(period);
            // 企业微信App的BUG，某一种模板的记录全部被删除后，筛选模板的组件不再显示，需要返回到工作台再进入汇报页
//            recordReportPage.backToWorkbenchPage().toReportPage();
            assertThat("删除全部" + period + "失败，当前我的" + period + "提交记录数不为0", records.size(), is(0));
            logger.info("删除全部{}成功", period);
        }

        @AfterEach
        @DisplayName("汇报页 -> 工作台页")
        void afterEach() {
            reportPage.backToWorkbenchPage();
            logger.info("从汇报页返回工作台页");
        }

        @AfterAll
        @DisplayName("工作台页 -> 消息页")
        void afterAll() {
            workbenchPage.backToMainPage();
            logger.info("从工作台页返回消息页");
        }
    }

    @AfterAll
    static void afterAll() {
        staticMainPage.quit();
        logger.info("退出应用");
    }
}
