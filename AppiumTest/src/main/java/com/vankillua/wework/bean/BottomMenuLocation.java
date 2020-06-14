package com.vankillua.wework.bean;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/14 13:04
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/bottom-menu-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "bottom")
public class BottomMenuLocation {
    private String messageMenu;
    private String contactMenu;
    private String workbenchMenu;
    private String mineMenu;

    public String getMessageMenu() {
        return messageMenu;
    }

    public void setMessageMenu(String messageMenu) {
        this.messageMenu = messageMenu;
    }

    public String getContactMenu() {
        return contactMenu;
    }

    public void setContactMenu(String contactMenu) {
        this.contactMenu = contactMenu;
    }

    public String getWorkbenchMenu() {
        return workbenchMenu;
    }

    public void setWorkbenchMenu(String workbenchMenu) {
        this.workbenchMenu = workbenchMenu;
    }

    public String getMineMenu() {
        return mineMenu;
    }

    public void setMineMenu(String mineMenu) {
        this.mineMenu = mineMenu;
    }
}
