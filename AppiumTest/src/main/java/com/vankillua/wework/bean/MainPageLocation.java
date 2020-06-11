package com.vankillua.wework.bean;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/8 18:00
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/main-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "main")
public class MainPageLocation {
    private String scheduleButton;
    private String todoButton;
    private String messageButton;
    private String workbenchButton;

    public String getScheduleButton() {
        return scheduleButton;
    }

    public void setScheduleButton(String scheduleButton) {
        this.scheduleButton = scheduleButton;
    }

    public String getTodoButton() {
        return todoButton;
    }

    public void setTodoButton(String todoButton) {
        this.todoButton = todoButton;
    }

    public String getMessageButton() {
        return messageButton;
    }

    public void setMessageButton(String messageButton) {
        this.messageButton = messageButton;
    }

    public String getWorkbenchButton() {
        return workbenchButton;
    }

    public void setWorkbenchButton(String workbenchButton) {
        this.workbenchButton = workbenchButton;
    }
}
