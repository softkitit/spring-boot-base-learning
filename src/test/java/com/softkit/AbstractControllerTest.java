package com.softkit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractControllerTest {
    @Autowired
    protected TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    public String getBaseUrl() {
        return "http://localhost:" + port;
    }

}
