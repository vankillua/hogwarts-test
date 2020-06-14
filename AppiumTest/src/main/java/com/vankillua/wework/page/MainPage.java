package com.vankillua.wework.page;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.BottomMenuLocation;
import com.vankillua.wework.bean.MainPageLocation;
import com.vankillua.wework.page.message.TodoPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/7 10:46
 * @Description
 *
 * 企业微信App首页（消息页）
 */
@Component(value = "weworkMainPage")
public class MainPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(MainPage.class);

    private MainPageLocation mainPageLocation;

    private BottomMenuLocation bottomMenuLocation;

    private TodoPage todoPage;

    private WorkbenchPage workbenchPage;

    @Autowired
    void setMainPageLocation(MainPageLocation mainPageLocation) {
        this.mainPageLocation = mainPageLocation;
    }

    @Autowired
    void setBottomMenuLocation(BottomMenuLocation bottomMenuLocation) {
        this.bottomMenuLocation = bottomMenuLocation;
    }

    @Autowired
    void setTodoPage(TodoPage todoPage) {
        this.todoPage = todoPage;
    }

    @Autowired
    void setWorkbenchPage(WorkbenchPage workbenchPage) {
        this.workbenchPage = workbenchPage;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MainPage waitForPage() {
        int times = WAIT_TIMES;
        do {
            if (isExists(mainPageLocation.getMoreButton())) {
                break;
            }
        } while (0 < --times);
        if (0 == times) {
            logger.warn("等待超时，消息页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MainPage setPrePage(BasePage currentPage) {
        return null;
    }

    public TodoPage toTodoPage() {
        click(mainPageLocation.getTodoButton());
        return todoPage.waitForPage().setPrePage(this);
    }

    public WorkbenchPage toWorkbenchPage() {
        click(bottomMenuLocation.getWorkbenchMenu());
        return workbenchPage.waitForPage().setPrePage(this);
    }
}
