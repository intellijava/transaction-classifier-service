package se.sbab.transaction.classifier.service.util.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonClassificationMapper {

  ObjectMapper objectMapper = new ObjectMapper();
  public Map<String, String> loadJson() {
    try {
      return objectMapper.readValue(new File("data/classifications.json"), Map.class);
    } catch (IOException e) {
      log.error("Error loading classifications from JSON file", e);
      e.printStackTrace();
    }
    return null;
  }

  public void saveJson(Map<String, String> classifications) {
    try {
      // Write the updated map back to the JSON file
      objectMapper.writeValue(new File("data/classifications.json"), classifications);
    } catch (IOException e) {
      log.error("Error saving classifications to JSON file", e);
      e.printStackTrace();
    }
  }
}
