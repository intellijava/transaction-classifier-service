package se.sbab.transaction.classifier.service.service;

  import java.util.Map;
  import java.util.Objects;
  import lombok.RequiredArgsConstructor;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.http.ResponseEntity;
  import org.springframework.stereotype.Service;
  import se.sbab.transaction.classifier.service.util.mapper.JsonClassificationMapper;
  import se.sbab.transaction.classifier.service.request.PostReqClassification;

  @Slf4j
  @Service
  @RequiredArgsConstructor
  public class ClassificationUpdater {

    private final JsonClassificationMapper jsonClassificationMapper;

    public ResponseEntity<String> updateRecipientClassification(PostReqClassification postReqClassification) {
      if (Objects.nonNull(postReqClassification.recipientId()) &&
          jsonClassificationMapper.loadJson().containsKey(postReqClassification.recipientId())) {
        updateRecipient(postReqClassification);
        return ResponseEntity.ok("Recipient classification updated for recipientId: " +
            postReqClassification.recipientId() +
            " with category: " + postReqClassification.category().toUpperCase());
      } else {
        return ResponseEntity.status(404).body("Recipient classification not found for recipientId: " +
            postReqClassification.recipientId());
      }
    }

    private void updateRecipient(PostReqClassification postReqClassification) {
      Map<String, String> updatedJson = jsonClassificationMapper.loadJson();
      updatedJson.put(postReqClassification.recipientId(), postReqClassification.category().toUpperCase());
      jsonClassificationMapper.saveJson(updatedJson);
      log.info("Classification updated for recipientId: {} with category: {}", postReqClassification.recipientId(),
          postReqClassification.category().toUpperCase());
    }
  }