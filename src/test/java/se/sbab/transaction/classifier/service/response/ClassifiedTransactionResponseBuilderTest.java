package se.sbab.transaction.classifier.service.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.sbab.transaction.classifier.service.datasource.api.transaction.model.Transaction;
import se.sbab.transaction.classifier.service.model.ClassifiedTransaction;
import se.sbab.transaction.classifier.service.util.mapper.JsonTransactionMapper;
import se.sbab.transaction.classifier.service.util.mapper.JsonClassificationMapper;

class ClassifiedTransactionResponseBuilderTest {

  @Mock
  private JsonTransactionMapper jsonTransactionMapper;

  @Mock
  private JsonClassificationMapper jsonClassificationMapper;

  @InjectMocks
  private ClassifiedTransactionResponseBuilder responseBuilder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void verifyReturnsClassifiedTransactionsWithinDateRange() {
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);
    var transaction = buildTransactionForDate(LocalDate.of(2025, 8, 10));

    when(jsonTransactionMapper.loadJson()).thenReturn(List.of(transaction));
    when(jsonClassificationMapper.loadJson()).thenReturn(Map.of("1", "Test Category"));

    List<ClassifiedTransaction> result = responseBuilder.build(startDate, endDate);

    assertEquals(1, result.size());
    assertEquals("Test Category", result.getFirst().category());
  }

  @Test
  void verifyReturnsClassifiedTransactionsWithinDateRangeAndDatesIncluded() {
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);
    var transaction = buildTransactionForDate(LocalDate.of(2025, 8, 10));
    var transactionOnStartDate = buildTransactionForDate(LocalDate.of(2025, 1, 1));
    var transactionOnEndDate = buildTransactionForDate(LocalDate.of(2025, 12, 31));


    when(jsonTransactionMapper.loadJson()).thenReturn(List.of(transaction, transactionOnStartDate, transactionOnEndDate));
    when(jsonClassificationMapper.loadJson()).thenReturn(Map.of("1", "Test Category"));

    List<ClassifiedTransaction> result = responseBuilder.build(startDate, endDate);

    assertEquals(3, result.size());
    assertEquals("Test Category", result.getFirst().category());
  }

  @Test
  void verifyReturnsEmptyListWhenNoTransactionsInDateRange() {
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);
    var transaction = buildTransactionForDate(LocalDate.of(2026, 6, 15));

    when(jsonTransactionMapper.loadJson()).thenReturn(List.of(transaction));
    when(jsonClassificationMapper.loadJson()).thenReturn(Map.of("1", "Test Category"));

    List<ClassifiedTransaction> result = responseBuilder.build(startDate, endDate);

    assertEquals(0, result.size());
  }

  @Test
  void verifyReturnsEmptyListWhenNoNegativeTransactions() {
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);
    var transaction = new Transaction(
        LocalDate.of(2025, 6, 15),
        "1",
        "Test Description",
        100L
    );

    when(jsonTransactionMapper.loadJson()).thenReturn(List.of(transaction));
    when(jsonClassificationMapper.loadJson()).thenReturn(Map.of("1", "Test Category"));

    List<ClassifiedTransaction> result = responseBuilder.build(startDate, endDate);

    assertEquals(0, result.size());
  }

  @Test
  void verifyReturnsListWhenNoClassificationsMatchWithNullCategory() {
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);
    var transaction = buildTransactionForDate(LocalDate.of(2025, 6, 15));

    when(jsonTransactionMapper.loadJson()).thenReturn(List.of(transaction));
    when(jsonClassificationMapper.loadJson()).thenReturn(Map.of());

    List<ClassifiedTransaction> result = responseBuilder.build(startDate, endDate);

    assertEquals(1, result.size());
    assertNull(result.getFirst().category());
  }

  private static Transaction buildTransactionForDate(LocalDate date) {
    return new Transaction(
        date,
        "1",
        "Test Description",
        -100L
    );
  }
}