package com.vankillua.wework.bean;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/8 18:29
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/todo-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "todo")
public class TodoPageLocation {
    private String addButton;
    private String backButton;
    private String todoList;
    private String completeButton;
    private String todoText;

    public String getAddButton() {
        return addButton;
    }

    public void setAddButton(String addButton) {
        this.addButton = addButton;
    }

    public String getBackButton() {
        return backButton;
    }

    public void setBackButton(String backButton) {
        this.backButton = backButton;
    }

    public String getTodoList() {
        return todoList;
    }

    public void setTodoList(String todoList) {
        this.todoList = todoList;
    }

    public String getCompleteButton() {
        return completeButton;
    }

    public void setCompleteButton(String completeButton) {
        this.completeButton = completeButton;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }
}
