package se.sbab.transaction.classifier.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
public record ClassifiedTransaction(
    @JsonProperty("date") LocalDate transactionDate,
    @JsonProperty("recipientId") String recipientId,
    @JsonProperty("description") String description,
    @JsonProperty("amount") Long amount,
    @JsonProperty("category") String category
) {
}