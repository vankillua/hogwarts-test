package com.vankillua.wework.page.message;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.message.SaveTodoPageLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/8 20:58
 * @Description
 *
 * 企业微信新建待办页
 */
@Component
public class SaveTodoPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(SaveTodoPage.class);

    private BasePage prePage;

    private SaveTodoPageLocation saveTodoPageLocation;

    @Autowired
    void setSaveTodoPageLocation(SaveTodoPageLocation saveTodoPageLocation) {
        this.saveTodoPageLocation = saveTodoPageLocation;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SaveTodoPage waitForPage() {
        if (!isExists(saveTodoPageLocation.getCloseButton())) {
            logger.warn("等待超时，新建待办页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SaveTodoPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    BasePage cancelAddTodo() {
        click(saveTodoPageLocation.getCloseButton());
        return prePage;
    }

    void addTodo(String todoText) {
        sendKeys(saveTodoPageLocation.getTodoTextInput(), true, todoText);
        click(saveTodoPageLocation.getSaveButton());
    }
}
