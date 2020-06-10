package com.vankillua.wework.page;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * @Author KILLUA
 * @Date 2020/6/8 21:51
 * @Description
 */
@SpringBootTest
@DisplayName("待办页测试")
class TodoPageTest {
    private static final Logger logger = LoggerFactory.getLogger(TodoPageTest.class);

    @Autowired
    private MainPage mainPage;

    private TodoPage todoPage;

    static MainPage staticMainPage;

    @PostConstruct
    void setStaticMainPage() {
        TodoPageTest.staticMainPage = mainPage;
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("待办页操作 - 新建、完成")
    class TodoOperation {
        @BeforeAll
        @DisplayName("进入待办页")
        void beforeAll() {
            todoPage = mainPage.toTodoPage();
            logger.info("进入待办页");
        }

        @ParameterizedTest(name = "新建【${0}】待办任务")
        @Order(1)
        @MethodSource(value = "todoTextProvider")
        @DisplayName("新建待办任务")
        void addTodo(String todoText) {
            List<String> todoTextList = todoPage.addTodo(todoText).getTodoTextList();
            assertThat("新建待办任务失败，当前待办任务数为0", todoTextList.size(), is(greaterThanOrEqualTo(1)));
            assertThat("当前待办列表第一项不是刚新建的待办任务", todoTextList.get(0), is(todoText));
            logger.info("新建【" + todoText + "】待办任务成功");
        }

        @ParameterizedTest(name = "完成【{0}】待办任务")
        @Order(2)
        @MethodSource(value = "completeTodoTextProvider")
        @DisplayName("完成一个待办任务")
        void completeOneTodo(String todoText) {
            List<String> list = todoPage.getTodoTextList();
            if (list.stream().anyMatch(s -> s.equals(todoText))) {
                int preSize = list.size();
                int currentSize = todoPage.completeTodo(todoText).getTodoList().size();
                assertThat("完成待办任务失败，当前待办任务数不等于原待办任务数减1", currentSize, is(preSize - 1));
                logger.info("完成【" + todoText + "】待办任务成功");
            } else {
                logger.warn("目标待办任务【" + todoText + "】不存在，请检查！");
            }
        }

        @Test
        @Order(3)
        @DisplayName("完成全部待办任务")
        void completeAllTodo() {
            int size = todoPage.completeAllTodo().getTodoList().size();
            assertThat("完成全部待办任务失败，当前待办任务数不为0", size, is(0));
            logger.info("完成全部待办任务成功");
        }

        private Stream<String> todoTextProvider() {
            return Stream.of("新建待办1", "新建待办2", "新建待办3");
        }

        private Stream<String> completeTodoTextProvider() {
            Object[] objects = todoTextProvider().toArray();
            if (1 < objects.length) {
                return Stream.of((String) objects[1]);
            } else if (1 == objects.length) {
                return Stream.of((String) objects[0]);
            } else {
                return Stream.of("新建待办");
            }
        }

        @AfterAll
        @DisplayName("返回主页")
        void afterAll() {
            todoPage.backToMainPage();
            logger.info("返回主页");
        }
    }

    @AfterAll
    static void afterAll() {
        staticMainPage.quit();
        logger.info("退出应用");
    }
}