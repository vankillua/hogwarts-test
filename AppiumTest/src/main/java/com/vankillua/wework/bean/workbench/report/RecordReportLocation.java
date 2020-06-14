package com.vankillua.wework.bean.workbench.report;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author KILLUA
 * @Date 2020/6/12 10:57
 * @Description
 */
@Component
@PropertySource(value = "classpath:wework/report-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "report.record")
public class RecordReportLocation {
    private String receiveTab;
    private String submitTab;

    private RecordReportLocation receive;
    private SubmitRecordLocation submit;

    public String getReceiveTab() {
        return receiveTab;
    }

    public void setReceiveTab(String receiveTab) {
        this.receiveTab = receiveTab;
    }

    public String getSubmitTab() {
        return submitTab;
    }

    public void setSubmitTab(String submitTab) {
        this.submitTab = submitTab;
    }

    public SubmitRecordLocation getSubmit() {
        return submit;
    }

    public void setSubmit(SubmitRecordLocation submit) {
        this.submit = submit;
    }

    public static class ReceiveRecordLocation {
        private String recordList;
        private String recordContent;

        public String getRecordList() {
            return recordList;
        }

        public void setRecordList(String recordList) {
            this.recordList = recordList;
        }

        public String getRecordContent() {
            return recordContent;
        }

        public void setRecordContent(String recordContent) {
            this.recordContent = recordContent;
        }
    }

    public static class SubmitRecordLocation {
        private String templateSelect;
        private String templateSelector;
        private String templateConfirmButton;
        private List<String> templateOptions;

        private String dateSelect;

        private String recordList;
        private String recordContent;

        public String getTemplateSelect() {
            return templateSelect;
        }

        public void setTemplateSelect(String templateSelect) {
            this.templateSelect = templateSelect;
        }

        public String getTemplateSelector() {
            return templateSelector;
        }

        public void setTemplateSelector(String templateSelector) {
            this.templateSelector = templateSelector;
        }

        public String getTemplateConfirmButton() {
            return templateConfirmButton;
        }

        public void setTemplateConfirmButton(String templateConfirmButton) {
            this.templateConfirmButton = templateConfirmButton;
        }

        public List<String> getTemplateOptions() {
            return templateOptions;
        }

        public void setTemplateOptions(List<String> templateOptions) {
            this.templateOptions = templateOptions;
        }

        public String getDateSelect() {
            return dateSelect;
        }

        public void setDateSelect(String dateSelect) {
            this.dateSelect = dateSelect;
        }

        public String getRecordList() {
            return recordList;
        }

        public void setRecordList(String recordList) {
            this.recordList = recordList;
        }

        public String getRecordContent() {
            return recordContent;
        }

        public void setRecordContent(String recordContent) {
            this.recordContent = recordContent;
        }

    }
}
