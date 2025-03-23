package se.sbab.transaction.classifier.service.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.sbab.transaction.classifier.service.model.ClassifiedTransaction;
import se.sbab.transaction.classifier.service.request.PostReqClassification;
import se.sbab.transaction.classifier.service.response.ClassifiedTransactionResponseBuilder;
import se.sbab.transaction.classifier.service.service.ClassificationUpdater;

@RestController
@RequestMapping("/v1/classifiedTransaction")
@RequiredArgsConstructor
public class ServiceController {

  private final ClassifiedTransactionResponseBuilder classifiedTransactionResponseBuilder;
  private final ClassificationUpdater classificationUpdater;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ClassifiedTransaction>> getClassifiedTransactions(
      @Parameter(description = "Start date (YYYY-MM-DD) of the period to classify transactions")
      @RequestParam(value = "startDate", defaultValue = "") LocalDate startDate,
      @Parameter(description = "End date (YYYY-MM-DD) of the period to classify transactions")
      @RequestParam(value = "endDate", defaultValue = "") LocalDate endDate
  ) {
    List<ClassifiedTransaction> classifiedTransactions =
        classifiedTransactionResponseBuilder.build(startDate, endDate);
    return ResponseEntity.ok(classifiedTransactions);
  }

  @PostMapping(value = "/recipient", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createTransaction(@RequestBody PostReqClassification postReqClassification) {
    var response = classificationUpdater.updateRecipientClassification(postReqClassification);
    return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
  }
}
