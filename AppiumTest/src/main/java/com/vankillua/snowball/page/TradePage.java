package com.vankillua.snowball.page;

import com.vankillua.common.BasePage;
import com.vankillua.snowball.bean.TradePageLocation;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author KILLUA
 * @Date 2020/7/2 16:13
 * @Description
 */
@Component(value = "TradePage")
public class TradePage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(TradePage.class);

    private BasePage prePage;

    private TradePageLocation tradePageLocation;

    @Autowired
    public void setTradePageLocation(TradePageLocation tradePageLocation) {
        this.tradePageLocation = tradePageLocation;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TradePage waitForPage() {
        if (!isExists(tradePageLocation.getHushenButton())) {
            logger.warn("等待超时，交易页仍未加载完成");
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TradePage setPrePage(BasePage currentPage) {
        prePage = currentPage;
        return this;
    }

    @Deprecated
    private void printContextAndSource(int times) {
        if (0 >= times) {
            times = 3;
        }
        for (int i = 0; i < times; i++) {
            logger.info(String.format("### getContext Handles %d ###", i));
            new ArrayList<>(driver.getContextHandles()).forEach(h -> {
                logger.info("Context Handle: {}", h);
                if (h.contains("WEBVIEW")) {
                    driver.context(h);
                    new ArrayList<>(driver.getWindowHandles()).forEach(logger::info);
                }
            });
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error("休眠时遇到异常：", e);
            }
        }
        logger.info(driver.getPageSource());
        driver.context("NATIVE_APP");
    }

    private boolean switchToWebViewAndWindow(String contextName, String title) {
        boolean isWebView = false;
        if (switchToWebView(contextName)) {
            if (switchToWindow(title)) {
                logger.info("切换WebView及窗口成功，将通过WebView定位进行操作");
                isWebView = true;
            } else {
                logger.warn("切换WebView成功，但切换窗口失败，将通过原生控件定位进行操作");
                switchToNativeApp();
            }
        } else {
            logger.warn("切换WebView失败，将通过原生控件定位进行操作");
        }
        return isWebView;
    }

    public boolean openAStockAccount() {
        boolean isWebView = switchToWebViewAndWindow(null, tradePageLocation.getWindowTitle());
        // 调试发现通过原生控件定位无法sendKeys到输入框中，因此切换到WebView失败直接返回
        if (!isWebView) {
            logger.error("切换WebView失败，则无法进行后续操作，直接返回操作失败");
            return false;
        }
        click(isWebView ? tradePageLocation.getOpenAStockWeb() : tradePageLocation.getOpenAStockNative());
        TradePageLocation.OpenStockPageLocation stockLocation = tradePageLocation.getAstock();
        try {
            // 在WebView中，则直接切换窗口到A股开户
            if (isWebView) {
                switchToWindow(stockLocation.getWindowTitle());
            }
            String phone = RandomStringUtils.randomNumeric(11);
            String code = RandomStringUtils.randomNumeric(6);
            logger.info("随机生成的手机号及验证码：{}, {}", phone, code);
            sendKeys(isWebView ? stockLocation.getPhoneInputWeb() : stockLocation.getPhoneInputNative(), true, phone);
            sendKeys(isWebView ? stockLocation.getCodeInputWeb() : stockLocation.getCodeInputNative(), true, code);
            click(isWebView ? stockLocation.getOpenButtonWeb() : stockLocation.getOpenButtonNative());
//            logger.info("点击【立即开户】按钮后，提示语为：{}", getToast());
            return true;
        } catch (Exception e) {
            logger.error("A股开户操作时遇到异常：", e);
            return false;
        } finally {
            try {
                if (isWebView) {
                    switchToNativeApp();
                }
                if (isExists(stockLocation.getBackButton())) {
                    click(stockLocation.getBackButton());
                }
            } catch (Exception ignored) {
            }
        }
    }
}
