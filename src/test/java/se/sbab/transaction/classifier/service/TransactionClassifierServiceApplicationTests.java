package se.sbab.transaction.classifier.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
class TransactionClassifierServiceApplicationTests {

  @Test
  void applicationContextLoadsSuccessfully() {
    try (ConfigurableApplicationContext context = SpringApplication.run(TransactionClassifierServiceApplication.class)) {
      assertNotNull(context);
    }
  }

  @Test
  void applicationStartsWithoutExceptions() {
    try (ConfigurableApplicationContext context = SpringApplication.run(TransactionClassifierServiceApplication.class)) {
      assertTrue(context.isRunning());
    }
  }
}
