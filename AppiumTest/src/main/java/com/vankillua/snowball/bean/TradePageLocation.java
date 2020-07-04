package com.vankillua.snowball.bean;

import com.vankillua.common.CommonPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/7/2 16:18
 * @Description
 */
@Component
@PropertySource(value = "classpath:snowball/trade-page-location-spring.yml", encoding = "utf-8", factory = CommonPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "trade")
public class TradePageLocation {
    /**
     * 沪深、港美、基金、模拟的按钮
     */
    private String hushenButton;
    private String gangmeiButton;
    private String jijinButton;
    private String moniButton;

    /**
     * 原生定位
     * A股开户、基金开户
     */
    private String openAStockNative;
    private String openFundNative;

    /**
     * WebView定位
     * 窗口标题
     * A股开户、基金开户
     */
    private String windowTitle;
    private String openAStockWeb;
    private String openFundWeb;

    /**
     * A股开户页面定位信息
     */
    private OpenStockPageLocation astock;

    public String getHushenButton() {
        return hushenButton;
    }

    public void setHushenButton(String hushenButton) {
        this.hushenButton = hushenButton;
    }

    public String getGangmeiButton() {
        return gangmeiButton;
    }

    public void setGangmeiButton(String gangmeiButton) {
        this.gangmeiButton = gangmeiButton;
    }

    public String getJijinButton() {
        return jijinButton;
    }

    public void setJijinButton(String jijinButton) {
        this.jijinButton = jijinButton;
    }

    public String getMoniButton() {
        return moniButton;
    }

    public void setMoniButton(String moniButton) {
        this.moniButton = moniButton;
    }

    public String getOpenAStockNative() {
        return openAStockNative;
    }

    public void setOpenAStockNative(String openAStockNative) {
        this.openAStockNative = openAStockNative;
    }

    public String getOpenFundNative() {
        return openFundNative;
    }

    public void setOpenFundNative(String openFundNative) {
        this.openFundNative = openFundNative;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public String getOpenAStockWeb() {
        return openAStockWeb;
    }

    public void setOpenAStockWeb(String openAStockWeb) {
        this.openAStockWeb = openAStockWeb;
    }

    public String getOpenFundWeb() {
        return openFundWeb;
    }

    public void setOpenFundWeb(String openFundWeb) {
        this.openFundWeb = openFundWeb;
    }

    /**
     * A股开户页面定位信息
     */
    public static class OpenStockPageLocation {
        /**
         * 返回按钮
         */
        private String backButton;

        /**
         * 原生定位
         * 手机号输入框、验证码输入框、立即开户按钮
         */
        private String phoneInputNative;
        private String codeInputNative;
        private String openButtonNative;

        /**
         * WebView定位
         * 窗口标题
         * 手机号输入框、验证码输入框、立即开户按钮
         */
        private String windowTitle;
        private String phoneInputWeb;
        private String codeInputWeb;
        private String openButtonWeb;

        public String getBackButton() {
            return backButton;
        }

        public void setBackButton(String backButton) {
            this.backButton = backButton;
        }

        public String getPhoneInputNative() {
            return phoneInputNative;
        }

        public void setPhoneInputNative(String phoneInputNative) {
            this.phoneInputNative = phoneInputNative;
        }

        public String getCodeInputNative() {
            return codeInputNative;
        }

        public void setCodeInputNative(String codeInputNative) {
            this.codeInputNative = codeInputNative;
        }

        public String getOpenButtonNative() {
            return openButtonNative;
        }

        public void setOpenButtonNative(String openButtonNative) {
            this.openButtonNative = openButtonNative;
        }

        public String getWindowTitle() {
            return windowTitle;
        }

        public void setWindowTitle(String windowTitle) {
            this.windowTitle = windowTitle;
        }

        public String getPhoneInputWeb() {
            return phoneInputWeb;
        }

        public void setPhoneInputWeb(String phoneInputWeb) {
            this.phoneInputWeb = phoneInputWeb;
        }

        public String getCodeInputWeb() {
            return codeInputWeb;
        }

        public void setCodeInputWeb(String codeInputWeb) {
            this.codeInputWeb = codeInputWeb;
        }

        public String getOpenButtonWeb() {
            return openButtonWeb;
        }

        public void setOpenButtonWeb(String openButtonWeb) {
            this.openButtonWeb = openButtonWeb;
        }
    }

    public OpenStockPageLocation getAstock() {
        return astock;
    }

    public void setAstock(OpenStockPageLocation astock) {
        this.astock = astock;
    }
}
