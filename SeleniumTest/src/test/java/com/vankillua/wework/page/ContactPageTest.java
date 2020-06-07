package com.vankillua.wework.page;

import com.vankillua.wework.AlertException;
import com.vankillua.wework.enums.TagShareEnum;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author KILLUA
 * @Date 2020/5/31 16:57
 * @Description
 */
@DisplayName("企业微信 - 通讯录测试")
class ContactPageTest {
    private static MainPage mainPage;
    private static ContactPage contactPage;

    @BeforeAll
    static void beforeAll() throws IOException {
        mainPage = new MainPage();
        contactPage = mainPage.toContactPage();
    }

    @AfterAll
    static void afterAll() {
        mainPage.quit();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("部门操作 - 新建和删除")
    class PartyOperation {
        String[] params = {"K社/test1/test11", "test111"};

        @Test
        @Order(1)
        @DisplayName("新建部门")
        void createParty() {
            System.setProperty("createParty", "失败");
            int result = 0;
            contactPage.refresh();
            String tips = contactPage.createParty(params[1], params[0]).getTips();
            assertThat(tips, is("新建部门成功"));
            List<WebElement> partyList = contactPage.search(params[1]).getSearchPartyList();
            for (WebElement party : partyList) {
                if (party.getAttribute("title").equals(String.join(ContactPage.SLASH, params))) {
                    result++;
                }
            }
            contactPage.cancelSearch();
            assertThat("新建部门失败", result, is(1));
            System.setProperty("createParty", "成功");
        }

        @Test
        @Order(2)
        @DisabledIfSystemProperty(named = "createParty", matches = "失败")
        @DisplayName("删除部门")
        void deleteParty() {
            int result = 0;
            contactPage.refresh();
            String tips = contactPage.deleteParty(String.join(ContactPage.SLASH, params)).getTips();
            assertThat(tips, is("删除部门成功"));
            List<WebElement> partyList = contactPage.search(params[1]).getSearchPartyList();
            for (WebElement party : partyList) {
                if (party.getAttribute("title").equals(String.join(ContactPage.SLASH, params))) {
                    result++;
                }
            }
            contactPage.cancelSearch();
            assertThat("删除部门失败", result, is(0));
        }
    }

    @Test
    @DisplayName("删除根部门")
    void deleteRootParty() {
        boolean result = false;
        String rootParty = "K社";
        contactPage.refresh();
        String tips = contactPage.deleteParty(rootParty).getTips();
        assertThat(tips, is("不允许删除根部门"));
        List<WebElement> partyList = contactPage.search(rootParty).getSearchPartyList();
        for (WebElement party : partyList) {
            if (party.getAttribute("title").equals(rootParty)) {
                result = true;
                break;
            }
        }
        contactPage.cancelSearch();
        assertThat("删除根部门不应该成功", result, is(true));
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("标签操作 - 新建和删除")
    class TagOperation {
        Object[] params = {"财务", TagShareEnum.SHARE_PRIVATE};

        @Test
        @Order(1)
        @DisplayName("新建标签前删除标签")
        void deleteNoTag() {
            String tagName = (String) params[0];
            contactPage.refresh();
            Throwable throwable = Assertions.assertThrows(AlertException.class, () -> {
                contactPage.deleteTag(tagName);
            });
            assertThat(throwable.getMessage(), containsString("在标签列表中没有标签"));
        }

        @Test
        @Order(2)
        @DisplayName("新建标签")
        void createTag() {
            System.setProperty("createTag", "失败");
            int result = 0;
            contactPage.refresh();
            String tips = contactPage.createTag((String) params[0], (TagShareEnum) params[1]).getTips();
            assertThat(tips, is("创建成功"));
            List<WebElement> tagList = contactPage.search((String) params[0]).getSearchTagList();
            for (WebElement tag : tagList) {
                if (tag.getText().equals(params[0])) {
                    result++;
                }
            }
            contactPage.cancelSearch();
            assertThat("新建标签失败", result, is(1));
            System.setProperty("createTag", "成功");
        }

        @Test
        @Order(3)
        @DisplayName("删除不存在的标签")
        void deleteNotExistTag() {
            String tagName = params[0] + "_1";
            contactPage.refresh();
            Throwable throwable = Assertions.assertThrows(AlertException.class, () -> {
                contactPage.deleteTag(tagName);
            });
            assertThat(throwable.getMessage(), containsString("在标签列表中找不到目标标签[" + tagName + "]"));
        }

        @Test
        @Order(4)
        @DisabledIfSystemProperty(named = "createTag", matches = "失败")
        @DisplayName("删除标签")
        void deleteTag() {
            String tagName = (String) params[0];
            int result = 0;
            contactPage.refresh();
            String tips = contactPage.deleteTag(tagName).getTips();
            assertThat(tips, is("删除成功"));
            List<WebElement> tagList = contactPage.search(tagName).getSearchTagList();
            for (WebElement tag : tagList) {
                if (tag.getText().equals(tagName)) {
                    result++;
                }
            }
            contactPage.cancelSearch();
            assertThat("删除标签失败", result, is(0));
        }
    }
}