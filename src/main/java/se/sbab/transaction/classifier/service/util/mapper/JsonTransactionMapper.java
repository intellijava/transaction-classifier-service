package se.sbab.transaction.classifier.service.util.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.List;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.sbab.transaction.classifier.service.datasource.api.transaction.model.Transaction;

@Slf4j
@Component
public class JsonTransactionMapper {

  private List<Transaction> transactions;

  @PostConstruct
  public void init() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    try {
      transactions = objectMapper.readValue(new File("data/transactions.json"), new TypeReference<>() {
      });
      log.info("Transactions loaded successfully");
    } catch (IOException e) {
      log.error("Error loading transactions from JSON file", e);
    }
  }

  public List<Transaction> loadJson() {
    return transactions;
  }
}