package com.vankillua.mock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vankillua.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @Author KILLUA
 * @Date 2020/7/12 17:44
 * @Description
 */
@Slf4j
public class ProxyTest {
//    private static final Logger logger = LoggerFactory.getLogger(ProxyTest.class);

    private static BrowserMobProxy browserMobProxy;
    private static final int HTTP_PORT = 8081;

    @BeforeAll
    static void beforeAll() {
        browserMobProxy = new BrowserMobProxyServer();
        browserMobProxy.start(HTTP_PORT);
        log.info("启动 BrowserMobProxyServer {}", HTTP_PORT);
    }

    @BeforeEach
    void beforeEach() {
        ((BrowserMobProxyServer) browserMobProxy).getFilterFactories().clear();
    }

    @Test
    @DisplayName("随机修改响应Json中的数字字段")
    void jsonNumberTest() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info("方法【{}】开始执行", methodName);
        browserMobProxy.addResponseFilter(
                (httpResponse, httpMessageContents, httpMessageInfo) -> {
                    if (httpMessageInfo.getUrl().contains("/v5/stock/batch/quote.json") &&
                            httpMessageContents.getContentType().contains("application/json")) {
                        JSON json = JSON.parseObject(httpMessageContents.getTextContents());
                        JsonMock jm = new JsonMock();
                        jm.mockJsonNumber(json, Arrays.asList("current", "percent"));
                        httpMessageContents.setTextContents(JSON.toJSONString(json, SerializerFeature.WriteMapNullValue));
                    }
                }
        );

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            log.error("线程休眠时遇到异常：", e);
        }
        log.info("方法【{}】结束执行", methodName);
    }

    @Test
    @DisplayName("随机修改响应Json中的字符串字段")
    void jsonStringTest() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info("方法【{}】开始执行", methodName);
        browserMobProxy.addResponseFilter(
                (httpResponse, httpMessageContents, httpMessageInfo) -> {
                    if (httpMessageInfo.getUrl().contains("/v5/stock/batch/quote.json") &&
                            httpMessageContents.getContentType().contains("application/json")) {
                        JSON json = JSON.parseObject(httpMessageContents.getTextContents());
                        JsonMock jm = new JsonMock();
                        jm.mockJsonString(json, Arrays.asList("name"));
                        httpMessageContents.setTextContents(JSON.toJSONString(json, SerializerFeature.WriteMapNullValue));
                    }
                }
        );

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            log.error("线程休眠时遇到异常：", e);
        }
        log.info("方法【{}】结束执行", methodName);
    }

    @ParameterizedTest
    @MethodSource("jsonArrayProvider")
    @DisplayName("修改响应Json数组的元素个数")
    void jsonArrayTest(int targetLevel, int mockType, int[] ints) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info("方法【{}】开始执行", methodName);
        browserMobProxy.addResponseFilter(
                (httpResponse, httpMessageContents, httpMessageInfo) -> {
                    if (httpMessageInfo.getUrl().contains("/v5/stock/batch/quote.json") &&
                            httpMessageContents.getContentType().contains("application/json")) {
                        JSON json = JSON.parseObject(httpMessageContents.getTextContents());
                        JsonMock jm = new JsonMock(ints);
                        jm.mockJsonArray(json, targetLevel, mockType);
                        httpMessageContents.setTextContents(JSON.toJSONString(json, SerializerFeature.WriteMapNullValue));
                    }
                }
        );

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            log.error("线程休眠时遇到异常：", e);
        }
        log.info("方法【{}】结束执行", methodName);
    }

    static Stream<Arguments> jsonArrayProvider() {
        int[] ints = new int[] {0, 1, 5, 15};
        return IntStream.range(0, ints.length).mapToObj(i -> arguments(1, i, ints));
    }

    @Test
    @DisplayName("修改响应Json对象为null")
    void jsonObjectTest() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info("方法【{}】开始执行", methodName);
        browserMobProxy.addResponseFilter(
                (httpResponse, httpMessageContents, httpMessageInfo) -> {
                    if (httpMessageInfo.getUrl().contains("/v5/stock/batch/quote.json") &&
                            httpMessageContents.getContentType().contains("application/json")) {
                        JSON json = JSON.parseObject(httpMessageContents.getTextContents());
                        JsonMock jm = new JsonMock();
                        jm.mockJsonObject(json, "market");
                        httpMessageContents.setTextContents(JSON.toJSONString(json, SerializerFeature.WriteMapNullValue));
                    }
                }
        );

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            log.error("线程休眠时遇到异常：", e);
        }
        log.info("方法【{}】结束执行", methodName);
    }

    @Test
    @Disabled
    void getStocksContent() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info("方法【{}】开始执行", methodName);
        browserMobProxy.addResponseFilter(
                (httpResponse, httpMessageContents, httpMessageInfo) -> {
                    log.info("Url: {}, ContentType: {}, Charset: {}", httpMessageInfo.getUrl(), httpMessageContents.getContentType(), httpMessageContents.getCharset().toString());
                    log.info("Contents: \n{}", httpMessageContents.getTextContents());
                }
        );

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            log.error("线程休眠时遇到异常：", e);
        }
        log.info("方法【{}】结束执行", methodName);
    }

    @Test
    @Disabled
    void randomTest() {
        log.info("随机正整数: {}", RandomUtils.randomPositiveInteger(4));
        log.info("随机负整数: {}", RandomUtils.randomNegativeInteger(5));
        log.info("随机整数: {}", RandomUtils.randomInteger(-10, 5));
        log.info("随机Float: {}", RandomUtils.randomFloat(3, 10));
        log.info("随机Float: {}", RandomUtils.randomFloat(-100, -50));
        log.info("随机英文字符串: {}", RandomUtils.randomEnglishString(8));
        log.info("随机中文字符串: {}", RandomUtils.randomChineseString(7));
        log.info("随机特殊字符串: {}", RandomUtils.randomSpecialString(6));
        log.info("中文字符串: {}", RandomUtils.randomSimplifiedChineseString(7));
    }

    @Test
    @Disabled
    void jsonTest() {
        String jsonStr = "{\"data\":{\"items\":[{\"market\":{\"status_id\":7,\"region\":\"CN\",\"status\":\"已收盘\",\"time_zone\":\"Asia/Shanghai\",\"time_zone_desc\":null},\"quote\":{\"symbol\":\"SH601318\",\"code\":\"601318\",\"exchange\":\"SH\",\"name\":\"中国平安\",\"type\":11,\"sub_type\":\"ASH\",\"status\":1,\"current\":82.36,\"currency\":\"CNY\",\"percent\":0.02,\"chg\":0.02,\"timestamp\":1594623600000,\"time\":1594623600000,\"lot_size\":100,\"tick_size\":0.01,\"open\":81.91,\"last_close\":82.34,\"high\":83.66,\"low\":81.51,\"avg_price\":82.454,\"volume\":117951579,\"amount\":9.725535191E9,\"turnover_rate\":1.09,\"amplitude\":2.61,\"market_capital\":1.505560682528E12,\"float_market_capital\":8.92178248055E11,\"total_shares\":18280241410,\"float_shares\":10832664498,\"issue_date\":1172678400000,\"lock_set\":null,\"current_year_percent\":-1.88,\"high52w\":90.8555,\"low52w\":64.8266,\"limit_up\":90.57,\"limit_down\":74.11,\"volume_ratio\":0.61,\"eps\":7.11,\"pe_ttm\":11.585,\"pe_forecast\":14.442,\"pe_lyr\":10.077,\"navps\":37.76,\"pb\":2.181,\"dividend\":2.05,\"dividend_yield\":2.489,\"profit\":1.49407E11,\"profit_four\":1.29953E11,\"profit_forecast\":1.04252E11,\"pledge_ratio\":3.36,\"goodwill_in_net_assets\":3.030153753426563},\"tags\":[]},{\"market\":{\"status_id\":2,\"region\":\"US\",\"status\":\"盘前交易\",\"time_zone\":\"America/New_York\",\"time_zone_desc\":null},\"quote\":{\"symbol\":\"BILI\",\"code\":\"BILI\",\"exchange\":\"NASDAQ\",\"name\":\"哔哩哔哩\",\"type\":0,\"sub_type\":\"1536\",\"status\":1,\"current\":47.55,\"currency\":\"USD\",\"percent\":-1.41,\"chg\":-0.68,\"timestamp\":1594411200632,\"time\":1594411200632,\"lot_size\":1,\"tick_size\":0.01,\"open\":47.79,\"last_close\":48.23,\"high\":48.21,\"low\":46.13,\"avg_price\":47.104,\"volume\":4514695,\"amount\":2.12660193E8,\"turnover_rate\":1.3,\"amplitude\":4.31,\"market_capital\":1.6520565205E10,\"float_market_capital\":null,\"total_shares\":347435651,\"float_shares\":null,\"issue_date\":1522166400000,\"lock_set\":1,\"current_year_percent\":155.37,\"high52w\":51.25,\"low52w\":13.23,\"variable_tick_size\":\"0.0001 1 0.01\",\"volume_ratio\":0.45,\"eps\":-0.09626856806701851,\"pe_ttm\":-70.576,\"pe_lyr\":-89.7,\"navps\":2.856056925670848,\"pb\":17.5219,\"dividend\":null,\"dividend_yield\":null,\"psr\":15.18,\"short_ratio\":3.85,\"inst_hld\":null,\"beta\":null,\"timestamp_ext\":1594628206013,\"current_ext\":47.9,\"percent_ext\":0.74,\"chg_ext\":0.35,\"contract_size\":100,\"pe_forecast\":-54.004,\"profit_forecast\":-3.059131826365273E8,\"profit\":-1.841758351670334E8,\"profit_four\":-2.3408310233475265E8,\"pledge_ratio\":null,\"goodwill_in_net_assets\":15.336848088936886,\"shareholder_funds\":9.428538564855828E8},\"tags\":[]},{\"market\":{\"status_id\":7,\"region\":\"CN\",\"status\":\"已收盘\",\"time_zone\":\"Asia/Shanghai\",\"time_zone_desc\":null},\"quote\":{\"symbol\":\"SH600519\",\"code\":\"600519\",\"exchange\":\"SH\",\"name\":\"贵州茅台\",\"type\":11,\"sub_type\":\"ASH\",\"status\":1,\"current\":1781.99,\"currency\":\"CNY\",\"percent\":3.98,\"chg\":68.14,\"timestamp\":1594623600000,\"time\":1594623600000,\"lot_size\":100,\"tick_size\":0.01,\"open\":1714.68,\"last_close\":1713.85,\"high\":1787.0,\"low\":1714.68,\"avg_price\":1758.597,\"volume\":6072716,\"amount\":1.0679460274E10,\"turnover_rate\":0.48,\"amplitude\":4.22,\"market_capital\":2.238531917622E12,\"float_market_capital\":2.238531917622E12,\"total_shares\":1256197800,\"float_shares\":1256197800,\"issue_date\":998841600000,\"lock_set\":0,\"current_year_percent\":52.4,\"high52w\":1787.0,\"low52w\":913.1165,\"limit_up\":1885.24,\"limit_down\":1542.47,\"volume_ratio\":1.25,\"eps\":34.29,\"pe_ttm\":51.964,\"pe_forecast\":42.74,\"pe_lyr\":54.325,\"navps\":118.69,\"pb\":15.014,\"dividend\":17.025,\"dividend_yield\":0.955,\"profit\":4.120647101443E10,\"profit_four\":4.307880988051E10,\"profit_forecast\":5.2375080845E10,\"pledge_ratio\":0.0,\"goodwill_in_net_assets\":null},\"tags\":[]}],\"items_size\":3},\"error_code\":0,\"error_description\":\"\"}";
//        String jsonStr = "{\"data\":{\"items\":[{\"market\":{\"status_id\":7,\"region\":\"CN\",\"status\":\"已收盘\",\"time_zone\":\"Asia/Shanghai\",\"time_zone_desc\":null},\"tags\":[{\"name\": \"abc\"}]},{\"market\":{\"status_id\":2,\"region\":\"US\",\"status\":\"盘前交易\",\"time_zone\":\"America/New_York\",\"time_zone_desc\":null},\"tags\":[{\"name\": \"bcd\"}, {\"name\": \"cde\"}]},{\"market\":{\"status_id\":7,\"region\":\"CN\",\"status\":\"已收盘\",\"time_zone\":\"Asia/Shanghai\",\"time_zone_desc\":null},\"tags\":[{\"name\": \"def\"}, {\"name\": \"efg\"}, {\"name\": \"fgh\"}]}],\"items_size\":3},\"error_code\":0,\"error_description\":\"\"}";
        log.info("输入的字符串: \n{}", jsonStr);
        Object object = JSON.parse(jsonStr);
        List<String> keys = new LinkedList<>();
//        JsonMockUtils.mockJsonNumber((JSON) object, keys);
//        JsonMockUtils.mockJsonString((JSON) object, keys);
//        JsonMockUtils.mockJsonArray((JSON) object, keys, 2, 0);
//        JsonMockUtils.mockJsonObject((JSON) object, keys, 4, 0);
//        JsonMockUtils.mockJsonObject((JSON) object, keys, "market");
        JsonMock jm = new JsonMock();
        jm.mockJsonNumber((JSON) object, Arrays.asList("current", "percent"));

        log.info("输出的字符串: \n{}", JSON.toJSONString(object, SerializerFeature.WriteMapNullValue));
    }

    @AfterAll
    static void afterAll() {
        if (null != browserMobProxy) {
            browserMobProxy.stop();
            log.info("关闭 BrowserMobProxyServer");
        }
    }
}
