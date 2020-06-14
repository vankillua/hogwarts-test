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
    private String moreButton;

    private String scheduleButton;
    private String todoButton;

    public String getMoreButton() {
        return moreButton;
    }

    public void setMoreButton(String moreButton) {
        this.moreButton = moreButton;
    }

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
}
