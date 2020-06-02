package com.vankillua.page;

import com.vankillua.AlertException;
import com.vankillua.enums.TagShareEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @Author KILLUA
 * @Date 2020/5/31 14:48
 * @Description
 */
public class ContactPage extends BasePage {
    public static final String SLASH = "/";
    public static final String PARTY_TAB = "组织架构";
    public static final String TAG_TAB = "标签";
    public static final String DELETE_PARTY_DIALOG_BODY = "是否删除 %s 部门？";

    /**
     * 提示语
     */
    private static final By TIPS = By.id("js_tips");

    /**
     * 对话框
     */
    private static final By DIALOG_HEAD = By.cssSelector(".qui_dialog>.qui_dialog_head");
    private static final By DIALOG_BODY = By.cssSelector(".qui_dialog>.qui_dialog_body");
    private static final By DIALOG_FOOT = By.cssSelector(".qui_dialog>.qui_dialog_foot");

    /**
     * SEARCH_INPUT: 搜索成员、部门输入框
     * SEARCH_TAG_INPUT: 搜索标签输入框
     * CLEAR_SEARCH_INPUT: 清空搜索输入框内容按钮
     * SWITCH_TAB: 切换组织架构、标签tab页
     * CURRENT_TAB: 当前tab页
     * SEARCH_RESULT: 搜索结果
     */
    private static final By SEARCH_INPUT = By.cssSelector(".member_colLeft_top>.ww_searchInput>[id=\"memberSearchInput\"]");
    private static final By SEARCH_TAG_INPUT = By.cssSelector(".member_colLeft_top>.ww_searchInput>.js_search_in");
    private static final By CLEAR_SEARCH_INPUT = By.id("clearMemberSearchInput");
    private static final By SWITCH_TAB = By.cssSelector(".member_colLeft_top>.member_colLeft_btnGroup");
    private static final By CURRENT_TAB = By.cssSelector("a.ww_btn_Active");
    private static final By SEARCH_RESULT = By.id("search_result_wrap");

    /**
     * "搜索成员、部门"输入框右边的"+"按钮
     */
    private static final By ADD_BUTTON = By.cssSelector(".member_colLeft_top_addBtnWrap");

    /**
     * 点击"+"按钮后，显示的创建部门按钮、创建标签按钮
     */
    private static final By CREATE_PARTY_BUTTON = By.cssSelector(".js_create_party");
    private static final By CREATE_TAG_BUTTON = By.cssSelector(".js_create_tag");

    /**
     * 点击创建部门按钮后，"新建部门"弹窗中的部门名称、所属部门
     */
    private static final By PARTY_NAME_INPUT = By.cssSelector("input[name=name]");
    private static final By PARENT_PARTY_DROPDOWN = By.cssSelector(".js_parent_party_name");
    private static final By PARENT_PARTY_TREE_LIST = By.cssSelector(".js_party_list_container");
    private static final String PARENT_PARTY_TREE_LEVEL = "ul.jstree-children>li.jstree-node[aria-level=\"%d\"]";
    private static final By PARENT_PARTY_TREE_WHOLE_ROW = By.cssSelector(".jstree-wholerow");
    private static final By PARENT_PARTY_TREE_ICON = By.cssSelector("i.jstree-icon");
    private static final By PARENT_PARTY_TREE_ANCHOR = By.cssSelector(".jstree-anchor");
    private static final By CONFIRM_BUTTON = By.linkText("确定");
    private static final By CANCEL_BUTTON = By.linkText("取消");

    /**
     * 点击创建标签按钮后，"新建标签"弹窗中的标签名称、可使用人
     */
    private static final By TAG_NAME_INPUT = By.cssSelector("input[name=name]");
    private static final By SHARE_RANGE_DROPDOWN = By.cssSelector(".js_toggle_share_range");
    private static final String SHARE_RANGE_ON_CLICK = "div.js_share_range_container a[on-click=\"%s\"]";

    /**
     * 左侧的部门树
     */
    private static final By PARTY_TREE_LIST = By.cssSelector(".member_colLeft_bottom");
    private static final String PARTY_TREE_LEVEL = "ul.jstree-children>li.jstree-node[aria-level=\"%d\"]";
    private static final By PARTY_TREE_WHOLE_ROW = By.cssSelector(".jstree-wholerow");
    private static final By PARTY_TREE_ICON = By.cssSelector("i.jstree-icon");
    private static final By PARTY_TREE_ANCHOR = By.cssSelector(".jstree-anchor");
    private static final By PARTY_TREE_CONTEXT_MENU = By.cssSelector(".jstree-contextmenu-hover");
    private static final By PARTY_TREE_DELETE_BUTTON = By.xpath("//ul[contains(@class, \"jstree-contextmenu\")]/li/a[text()=\"删除\"]");

    /**
     * 右侧的部门成员列表
     */
    private static final By PARTY_NO_MEMBER = By.cssSelector(".member_colRight .js_no_member");

    /**
     * 左侧的标签列表
     */
    private static final By TAG_LIST = By.cssSelector(".member_tag_list>li");
    private static final By TAG_MORE_BUTTON = By.cssSelector("a.member_tag_list_moreBtn");
    private static final By TAG_DELETE_BUTTON = By.xpath("//ul[contains(@class, \"jstree-contextmenu\")]/li/a[text()=\"删除\"]");

    /**
     * 左侧搜索列表（成员、部门、标签）
     */
    private static final By SEARCH_MEMBER_LIST = By.id("search_member_list");
    private static final By SEARCH_PARTY_LIST = By.id("search_party_list");
    private static final By SEARCH_TAG_LIST = By.id("search_tag_list");

    public ContactPage(RemoteWebDriver driver) {
        super(driver);
    }

    public ContactPage(RemoteWebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public ContactPage refresh() {
        driver.navigate().refresh();
        return this;
    }

    public String getTips() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TIPS)).getText();
    }

    ContactPage search(String keyword) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5, 500);
        // 判断TAB切换组件是否存在
        try {
            WebElement switchTab = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SWITCH_TAB));
            // 切换到组织架构tab页
            click(switchTab.findElement(By.linkText(PARTY_TAB)));
        } catch (TimeoutException ignored) {
            // 没有tab页
        }
        sendKeys(SEARCH_INPUT, keyword);
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_RESULT));
        } catch (TimeoutException ignored) {
            // 忽略超时异常
        }
        return this;
    }

    ContactPage cancelSearch() {
        if (wait.until(ExpectedConditions.presenceOfElementLocated(CLEAR_SEARCH_INPUT)).isDisplayed()) {
            click(CLEAR_SEARCH_INPUT);
        }
        return this;
    }

    List<WebElement> getSearchMemberList() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_MEMBER_LIST)).findElements(By.cssSelector("li"));
    }

    List<WebElement> getSearchPartyList() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_PARTY_LIST)).findElements(By.cssSelector("li"));
    }

    List<WebElement> getSearchTagList() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_TAG_LIST)).findElements(By.cssSelector("li"));
    }

    ContactPage createParty(String partyName, String parentPartyName) {
        // 入参判断
        if (null == partyName || partyName.isEmpty()) {
            throw new AlertException("输入的部门名称为空");
        } else if (null == parentPartyName || parentPartyName.isEmpty()) {
            throw new AlertException("输入的所属部门为空");
        }

        // 点击"+"按钮，点击添加部门
        click(ADD_BUTTON);
        click(CREATE_PARTY_BUTTON);
        sendKeys(PARTY_NAME_INPUT, partyName);
        String[] parentParties = parentPartyName.split(SLASH);
        // 点击"选择所属部门"后，从下拉列表中选择目标的所属部门
        click(PARENT_PARTY_DROPDOWN);
        WebElement root = wait.until(ExpectedConditions.visibilityOfElementLocated(PARENT_PARTY_TREE_LIST));
        WebElement parentParty = null;
        for (int i = 1; i <= parentParties.length; i++) {
            boolean found = false;
            By parentPartyTree = getSelector(String.format(PARENT_PARTY_TREE_LEVEL, i));
            for (WebElement element : wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(root, parentPartyTree))) {
                boolean isLeaf = element.getAttribute("class").contains("jstree-leaf");
                boolean isClosed = element.getAttribute("class").contains("jstree-closed");
                WebElement anchor = element.findElement(PARENT_PARTY_TREE_ANCHOR);
                if (parentParties[i - 1].equals(anchor.getText())) {
                    if (!isLeaf && isClosed) {
                        click(element.findElement(PARENT_PARTY_TREE_ICON));
                    }
                    parentParty = element;
                    found = true;
                    break;
                }
            }
            if (!found) {
                parentParty = null;
                break;
            }
        }
        if (null != parentParty) {
            click(parentParty.findElement(PARENT_PARTY_TREE_ANCHOR));
            click(CONFIRM_BUTTON);
        } else {
            click(CANCEL_BUTTON);
            throw new NoSuchElementException("在所属部门下拉列表中找不到目标部门[" + parentPartyName + "]，请检查！");
        }
        return this;
    }

    ContactPage deleteParty(String partyName) {
        // 入参判断
        if (null == partyName || partyName.isEmpty()) {
            throw new AlertException("输入的部门名称为空");
        }

        // 判断TAB切换组件是否存在
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, 3, 500);
            WebElement switchTab = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SWITCH_TAB));
            click(switchTab.findElement(By.linkText(PARTY_TAB)));
        } catch (TimeoutException ignored) {
        }

        String[] parties = partyName.split(SLASH);
        WebElement root = wait.until(ExpectedConditions.visibilityOfElementLocated(PARTY_TREE_LIST));
        WebElement party = null;
        for (int i = 1; i <= parties.length; i++) {
            boolean found = false;
            By partyTree = getSelector(String.format(PARTY_TREE_LEVEL, i));
            for (WebElement element : wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(root, partyTree))) {
                boolean isLeaf = element.getAttribute("class").contains("jstree-leaf");
                boolean isClosed = element.getAttribute("class").contains("jstree-closed");
                WebElement anchor = element.findElement(PARTY_TREE_ANCHOR);
                if (parties[i - 1].equals(anchor.getText())) {
                    if (!isLeaf && isClosed) {
                        click(element.findElement(PARTY_TREE_ICON));
                    }
                    party = element;
                    found = true;
                    break;
                }
            }
            if (!found) {
                party = null;
                break;
            }
        }
        if (null != party) {
            WebElement anchor = party.findElement(PARTY_TREE_ANCHOR);
            String message = String.format(DELETE_PARTY_DIALOG_BODY, anchor.getText());
            click(anchor);
            moveToClick(anchor.findElement(PARTY_TREE_CONTEXT_MENU));
            click(PARTY_TREE_DELETE_BUTTON);
            // 删除根部门时，不会弹出对话框
            if (1 < parties.length) {
                String dialogBodyText = wait.until(ExpectedConditions.visibilityOfElementLocated(DIALOG_BODY)).getText();
                // 能不能删除对话框中都有"确定"按钮
                click(CONFIRM_BUTTON);
                if (!dialogBodyText.equals(message)) {
                    throw new AlertException(dialogBodyText);
                }
            }
        } else {
            throw new NoSuchElementException("在部门树中找不到目标部门[" + partyName + "]，请检查！");
        }
        return this;
    }

    ContactPage createTag(String tagName, TagShareEnum shareType) {
        // 入参判断
        if (null == tagName || tagName.isEmpty()) {
            throw new AlertException("输入的标签名称为空");
        }

        // 判断TAB切换组件是否存在
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, 3, 500);
            WebElement switchTab = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SWITCH_TAB));
            // 点击"标签"tab页，点击"+"按钮
            click(switchTab.findElement(By.linkText(TAG_TAB)));
            click(ADD_BUTTON);
        } catch (TimeoutException ignored) {
            // 点击"+"按钮，点击添加标签
            click(ADD_BUTTON);
            click(CREATE_TAG_BUTTON);
        }

        sendKeys(TAG_NAME_INPUT, tagName);
        // 点击"可使用人"后，从下拉列表中选择目标的分享范围
        click(SHARE_RANGE_DROPDOWN);
        By share = getSelector(String.format(SHARE_RANGE_ON_CLICK, shareType.getOnClick()));
        click(share);
        // "可使用人"选择了指定用户，则需要选择成员
        if (shareType.equals(TagShareEnum.SHARE_RANGE)) {
            throw new AlertException("暂无可设置的成员");
        }
        click(CONFIRM_BUTTON);
        return this;
    }

    ContactPage deleteTag(String tagName) {
        // 入参判断
        if (null == tagName || tagName.isEmpty()) {
            throw new AlertException("输入的标签名称为空");
        }

        WebDriverWait webDriverWait = new WebDriverWait(driver, 3, 500);
        // 判断TAB切换组件是否存在
        try {
            WebElement switchTab = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SWITCH_TAB));
            // 点击"标签"tab页
            click(switchTab.findElement(By.linkText(TAG_TAB)));
        } catch (TimeoutException ignored) {
            throw new AlertException("标签tab页不存在，请检查！");
        }

        try {
            boolean found = false;
            for (WebElement element : webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(TAG_LIST))) {
                if (element.getText().equals(tagName)) {
                    found = true;
                    click(element);
                    click(element.findElement(TAG_MORE_BUTTON));
                    click(TAG_DELETE_BUTTON);
                    click(CONFIRM_BUTTON);
                    break;
                }
            }
            if (!found) {
                throw new AlertException("在标签列表中找不到目标标签[" + tagName + "]，请检查！");
            }
        } catch (TimeoutException ignored) {
            throw new AlertException("在标签列表中没有标签");
        }
        return this;
    }
}
