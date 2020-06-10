package com.vankillua.wework.bean;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/8 20:59
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/todo-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "todo.save")
public class SaveTodoPageLocation {
    private String closeButton;
    private String saveButton;
    private String todoTextInput;

    public String getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(String closeButton) {
        this.closeButton = closeButton;
    }

    public String getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(String saveButton) {
        this.saveButton = saveButton;
    }

    public String getTodoTextInput() {
        return todoTextInput;
    }

    public void setTodoTextInput(String todoTextInput) {
        this.todoTextInput = todoTextInput;
    }
}
