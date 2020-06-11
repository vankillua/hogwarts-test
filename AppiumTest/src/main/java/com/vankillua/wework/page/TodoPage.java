package com.vankillua.wework.page;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.TodoPageLocation;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author KILLUA
 * @Date 2020/6/8 18:21
 * @Description
 *
 * 企业微信待办页
 */
@Component
public class TodoPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(TodoPage.class);

    private BasePage prePage;

    private TodoPageLocation todoPageLocation;

    private SaveTodoPage saveTodoPage;

    @Autowired
    void setTodoPageLocation(TodoPageLocation todoPageLocation) {
        this.todoPageLocation = todoPageLocation;
    }

    @Autowired
    void setSaveTodoPage(SaveTodoPage saveTodoPage) {
        this.saveTodoPage = saveTodoPage;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected TodoPage waitForPage() {
        int times = WAIT_TIMES;
        do {
            if (isExists(todoPageLocation.getBackButton())) {
                break;
            }
        } while (0 < --times);
        if (0 == times) {
            logger.error("等待超时，待办页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected TodoPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    TodoPage addTodo(String todoText) {
        click(todoPageLocation.getAddButton());
        saveTodoPage.addTodo(todoText);
        // 等待todoPageLocation.getAddButton()出现再结束此方法
        int times = WAIT_TIMES;
        do {
            if (isExists(todoPageLocation.getAddButton())) {
                break;
            }
        } while (0 < --times);
        if (0 == times) {
            logger.warn("等待超时，未从新建待办页返回到待办页");
        }
        return this;
    }

    TodoPage completeTodo(String todoText) {
        for (MobileElement element : getTodoList()) {
            String text = find(element, todoPageLocation.getTodoText()).getText();
            if (todoText.equals(text)) {
                click(find(element, todoPageLocation.getCompleteButton()));
                int times = WAIT_TIMES;
                // 等待待办任务消失
                do {
                    try {
                        // 待办任务[element]不存在后再去找完成按钮会抛出StaleElementReferenceException异常
                        if (!isExists(element, todoPageLocation.getCompleteButton(), 3)) {
                            break;
                        }
                    } catch (StaleElementReferenceException ignored) {
                        break;
                    }
                } while (0 < --times);
                break;
            }
        }
        return this;
    }

    TodoPage completeAllTodo() {
        List<MobileElement> elements = getTodoList();
        while (!elements.isEmpty()) {
            MobileElement element = elements.get(0);
            click(find(element, todoPageLocation.getCompleteButton()));
            int times = WAIT_TIMES;
            // 等待待办任务消失
            do {
                try {
                    // 待办任务[element]不存在后再去找完成按钮会抛出StaleElementReferenceException异常
                    if (!isExists(element, todoPageLocation.getCompleteButton(), 3)) {
                        break;
                    }
                } catch (StaleElementReferenceException ignored) {
                    break;
                }
            } while (0 < --times);
            elements = getTodoList();
        }
        return this;
    }

    BasePage backToMainPage() {
        click(todoPageLocation.getBackButton());
        return prePage;
    }

    List<String> getTodoTextList() {
        return getTodoList()
                .stream()
                .map(e -> find(e, todoPageLocation.getTodoText()).getText())
                .collect(Collectors.toList());
    }

    List<MobileElement> getTodoList() {
        return finds(todoPageLocation.getTodoList(), 2);
    }
}
