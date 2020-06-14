package com.vankillua.wework.bean.workbench.report.record;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/13 18:32
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/report-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "report.record.detail")
public class RecordDetailLocation {
    private String backButton;
    private String moreButton;

    private String modifyButton;
    private String forwardButton;
    private String deleteButton;

    private String confirmButton;

    public String getBackButton() {
        return backButton;
    }

    public void setBackButton(String backButton) {
        this.backButton = backButton;
    }

    public String getMoreButton() {
        return moreButton;
    }

    public void setMoreButton(String moreButton) {
        this.moreButton = moreButton;
    }

    public String getModifyButton() {
        return modifyButton;
    }

    public void setModifyButton(String modifyButton) {
        this.modifyButton = modifyButton;
    }

    public String getForwardButton() {
        return forwardButton;
    }

    public void setForwardButton(String forwardButton) {
        this.forwardButton = forwardButton;
    }

    public String getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(String deleteButton) {
        this.deleteButton = deleteButton;
    }

    public String getConfirmButton() {
        return confirmButton;
    }

    public void setConfirmButton(String confirmButton) {
        this.confirmButton = confirmButton;
    }
}
