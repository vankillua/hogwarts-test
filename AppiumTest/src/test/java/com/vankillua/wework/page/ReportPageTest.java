package com.vankillua.wework.page;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author KILLUA
 * @Date 2020/6/11 17:03
 * @Description
 */
@SpringBootTest
@DisplayName("汇报页测试")
public class ReportPageTest {
    private static final Logger logger = LoggerFactory.getLogger(TodoPageTest.class);

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
    @DisplayName("汇报页操作 - 日报")
    class dailyReportOperation {
        @BeforeAll
        @DisplayName("进入工作台 -> 汇报页")
        void beforeAll() {
            reportPage = mainPage.toWorkbenchPage().toReportPage();
            logger.info("进入汇报页");
        }

        @Test
        void addDailyReport() {
            SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
            String todayJob = sdf.format(new Date()) + "工作日报";
            reportPage.addDailyReport(todayJob);
            logger.info("新建日报【" + todayJob + "】成功");
        }

        @AfterAll
        void afterAll() {
            reportPage.backToWorkbenchPage();
            logger.info("返回工作台页");
        }
    }

    @AfterAll
    static void afterAll() {
        staticMainPage.quit();
        logger.info("退出应用");
    }
}
