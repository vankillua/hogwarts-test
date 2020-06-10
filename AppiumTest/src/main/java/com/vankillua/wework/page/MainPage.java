package com.vankillua.wework.page;

import com.vankillua.common.BasePage;
import com.vankillua.wework.bean.MainPageLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author KILLUA
 * @Date 2020/6/7 10:46
 * @Description
 *
 * 企业微信App首页
 */
@Component(value = "weworkMainPage")
public class MainPage extends BasePage {
    /**
     * 首页定位元素
     */
    private MainPageLocation mainPageLocation;

    private TodoPage todoPage;

    @Autowired
    void setMainPageLocation(MainPageLocation mainPageLocation) {
        this.mainPageLocation = mainPageLocation;
    }

    @Autowired
    void setTodoPage(TodoPage todoPage) {
        this.todoPage = todoPage;
    }

    TodoPage toTodoPage() {
        click(mainPageLocation.getTodo());
        return todoPage.setPrePage(this);
    }
}
