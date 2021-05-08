package com.softkit;


import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ImportAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        TransactionAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@DirtiesContext
public class ContextLoadTest {

    @Test
    public void contextLoads() {
        try {
            StarterApplication.main(new String[]
                    {
                            "--server.port=0"
                    });
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
