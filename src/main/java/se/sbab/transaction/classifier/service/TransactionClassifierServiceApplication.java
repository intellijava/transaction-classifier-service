package se.sbab.transaction.classifier.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Transaction Classifier Service"))
public class TransactionClassifierServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TransactionClassifierServiceApplication.class, args);
  }
}
