package se.sbab.transaction.classifier.service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import se.sbab.transaction.classifier.service.request.PostReqClassification;
import se.sbab.transaction.classifier.service.util.mapper.JsonClassificationMapper;

class ClassificationUpdaterTest {

  @Mock
  private JsonClassificationMapper jsonClassificationMapper;

  @InjectMocks
  private ClassificationUpdater classificationUpdater;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void verifyUpdatesClassificationWhenRecipientExists() {
    PostReqClassification postReqClassification = new PostReqClassification("1", "newCategory");
    Map<String, String> classifications = new HashMap<>();
    classifications.put("1", "oldCategory");
    when(jsonClassificationMapper.loadJson()).thenReturn(classifications);

    ResponseEntity<String> response = classificationUpdater.updateRecipientClassification(postReqClassification);

    assertEquals(ResponseEntity.ok("Recipient classification updated for recipientId: 1 with category: NEWCATEGORY"),
        response);
    assertEquals("NEWCATEGORY", classifications.get("1"));
  }

  @Test
  void verifyReturnsNotFoundWhenRecipientDoesNotExist() {
    PostReqClassification postReqClassification = new PostReqClassification("2", "newCategory");
    Map<String, String> classifications = new HashMap<>();
    classifications.put("1", "oldCategory");
    var expected = ResponseEntity.status(404).body("Recipient classification not found for recipientId: " +
        postReqClassification.recipientId());

    when(jsonClassificationMapper.loadJson()).thenReturn(classifications);

    ResponseEntity<String> response = classificationUpdater.updateRecipientClassification(postReqClassification);

    assertEquals(expected, response);
  }

  @Test
  void verifyReturnsNotFoundWhenRecipientIdIsNull() {
    PostReqClassification postReqClassification = new PostReqClassification(null, "newCategory");
    Map<String, String> classifications = new HashMap<>();
    classifications.put("1", "oldCategory");
    var expected = ResponseEntity.status(404).body("Recipient classification not found for recipientId: " +
        postReqClassification.recipientId());

    when(jsonClassificationMapper.loadJson()).thenReturn(classifications);

    ResponseEntity<String> response = classificationUpdater.updateRecipientClassification(postReqClassification);

    assertEquals(expected, response);
  }

  @Test
  void verifyUpdatesClassificationToUpperCase() {
    PostReqClassification postReqClassification = new PostReqClassification("1", "newCategory");
    Map<String, String> classifications = new HashMap<>();
    classifications.put("1", "oldCategory");
    when(jsonClassificationMapper.loadJson()).thenReturn(classifications);

    classificationUpdater.updateRecipientClassification(postReqClassification);

    assertEquals("NEWCATEGORY", classifications.get("1"));
  }
}