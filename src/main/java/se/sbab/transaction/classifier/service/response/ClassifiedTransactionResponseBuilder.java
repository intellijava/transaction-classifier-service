package se.sbab.transaction.classifier.service.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.sbab.transaction.classifier.service.util.mapper.JsonTransactionMapper;
import se.sbab.transaction.classifier.service.util.mapper.JsonClassificationMapper;
import se.sbab.transaction.classifier.service.datasource.api.transaction.model.Transaction;
import se.sbab.transaction.classifier.service.model.ClassifiedTransaction;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassifiedTransactionResponseBuilder {

  private final JsonTransactionMapper jsonTransactionMapper;
  private final JsonClassificationMapper jsonClassificationMapper;

  public List<ClassifiedTransaction> build(LocalDate startDate, LocalDate endDate) {
    List<ClassifiedTransaction> classifiedTransactions = new ArrayList<>();
    var transactions = jsonTransactionMapper.loadJson();
    Map<String, String> classifications = jsonClassificationMapper.loadJson();
    transactions.stream()
        .filter(transaction -> validateDates(transaction, startDate, endDate))
        .filter(transaction -> transaction.amount() < 0)
        .forEach(transaction -> {
          var cTrans = buildClassifiedTransaction(transaction, classifications);
          classifiedTransactions.add(cTrans);
        });
    log.info("Total of: {} Classified transactions found for given dates", classifiedTransactions.size());
    return classifiedTransactions;
  }

  private boolean validateDates(Transaction transaction, LocalDate startDate, LocalDate endDate) {
    return Objects.nonNull(startDate) &&
        Objects.nonNull(endDate) &&
        transaction.transactionDate().isAfter(startDate) &&
        transaction.transactionDate().isBefore(endDate);
  }

  private ClassifiedTransaction buildClassifiedTransaction(Transaction transaction,
      Map<String, String> classifications) {
    return new ClassifiedTransaction(
        transaction.transactionDate(),
        transaction.recipientId(),
        transaction.description(),
        transaction.amount(),
        classifications.get(transaction.recipientId())
    );
  }
}