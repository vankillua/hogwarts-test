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
    private String schedule;
    private String todo;
    private String workbench;

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getWorkbench() {
        return workbench;
    }

    public void setWorkbench(String workbench) {
        this.workbench = workbench;
    }
}
