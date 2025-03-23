package se.sbab.transaction.classifier.service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import se.sbab.transaction.classifier.service.model.ClassifiedTransaction;
import se.sbab.transaction.classifier.service.request.PostReqClassification;
import se.sbab.transaction.classifier.service.response.ClassifiedTransactionResponseBuilder;
import se.sbab.transaction.classifier.service.service.ClassificationUpdater;

class ServiceControllerTest {

  @Mock
  private ClassifiedTransactionResponseBuilder classifiedTransactionResponseBuilder;

  @Mock
  private ClassificationUpdater classificationUpdater;

  @InjectMocks
  private ServiceController serviceController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getClassifiedTransactionsReturnsTransactionsWithinDateRange() {
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);
    List<ClassifiedTransaction> transactions = List.of(new ClassifiedTransaction(LocalDate.of(2025, 8, 10),"1", "Test"
        + " Category", 100L, "Test"));

    when(classifiedTransactionResponseBuilder.build(startDate, endDate)).thenReturn(transactions);

    ResponseEntity<List<ClassifiedTransaction>> response = serviceController.getClassifiedTransactions(startDate, endDate);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(transactions, response.getBody());
  }

  @Test
  void getClassifiedTransactionsReturnsEmptyListWhenNoTransactionsInDateRange() {
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);

    when(classifiedTransactionResponseBuilder.build(startDate, endDate)).thenReturn(List.of());

    ResponseEntity<List<ClassifiedTransaction>> response = serviceController.getClassifiedTransactions(startDate, endDate);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(0, Objects.requireNonNull(response.getBody()).size());
  }

  @Test
  void createTransactionReturnsSuccessResponse() {
    PostReqClassification postReqClassification = new PostReqClassification("1", "Test Category");
    var response = ResponseEntity.ok("Success");

    when(classificationUpdater.updateRecipientClassification(postReqClassification)).thenReturn(response);

    ResponseEntity<String> result = serviceController.createTransaction(postReqClassification);

    assertEquals(200, result.getStatusCode().value());
    assertEquals("Success", result.getBody());
  }

  @Test
  void createTransactionReturnsErrorResponse() {
    PostReqClassification postReqClassification = new PostReqClassification("1", "Test Category");
    var response = ResponseEntity.status(400).body("Recipient classification not found for recipientId");

    when(classificationUpdater.updateRecipientClassification(postReqClassification)).thenReturn(response);

    ResponseEntity<String> result = serviceController.createTransaction(postReqClassification);

    assertEquals(400, result.getStatusCode().value());
    assertEquals("Recipient classification not found for recipientId", result.getBody());
  }
}