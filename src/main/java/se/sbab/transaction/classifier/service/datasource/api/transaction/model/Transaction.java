package se.sbab.transaction.classifier.service.datasource.api.transaction.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
public record Transaction(
    @JsonProperty("date") LocalDate transactionDate,
    @JsonProperty("recipientId") String recipientId,
    @JsonProperty("description") String description,
    @JsonProperty("amount") Long amount
) {
}