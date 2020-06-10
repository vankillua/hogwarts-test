package com.vankillua.wework.page;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.TodoPageLocation;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    TodoPage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    TodoPage addTodo(String todoText) {
        click(todoPageLocation.getAddButton());
        saveTodoPage.saveTodo(todoText);
        // 等待todoPageLocation.getAddButton()出现再结束此方法
        int times = 3;
        do {
            if (isExists(todoPageLocation.getAddButton())) {
                break;
            }
        } while (0 < --times);
        return this;
    }

    TodoPage completeTodo(String todoText) {
        for (MobileElement element : getTodoList()) {
            String text = find(element, todoPageLocation.getTodoText()).getText();
            if (todoText.equals(text)) {
                click(find(element, todoPageLocation.getCompleteButton()));
                int times = 3;
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
            int times = 3;
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
