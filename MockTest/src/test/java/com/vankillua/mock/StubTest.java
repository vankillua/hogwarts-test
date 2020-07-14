package com.vankillua.mock;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * @Author KILLUA
 * @Date 2020/7/12 15:00
 * @Description
 */
public class StubTest {
//    static WireMockRule wireMockRule;
    static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll() {
        // No-args constructor defaults to port 8080
//        wireMockRule = new WireMockRule(8089);
        //No-args constructor will start on port 8080, no HTTPS
        wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        wireMockServer.start();
    }

    @Test
    void stub() {
        wireMockServer.stubFor(get(urlEqualTo("/stub"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("<h1>stub test</h1>")));

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void proxy() {
        wireMockServer.stubFor(get(urlMatching(".*"))
                .willReturn(aResponse().proxiedFrom("https://www.baidu.com")));

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void afterAll() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }
}
