package com.vankillua.wework.page;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.SaveTodoPageLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/8 20:58
 * @Description
 */
@Component
public class SaveTodoPage extends BasePage {
    private SaveTodoPageLocation saveTodoPageLocation;

    @Autowired
    void setSaveTodoPageLocation(SaveTodoPageLocation saveTodoPageLocation) {
        this.saveTodoPageLocation = saveTodoPageLocation;
    }

    void cancelSaveTodo() {
        click(saveTodoPageLocation.getCloseButton());
    }

    void saveTodo(String todoText) {
        sendKeys(saveTodoPageLocation.getTodoTextInput(), true, todoText);
        click(saveTodoPageLocation.getSaveButton());
    }
}
