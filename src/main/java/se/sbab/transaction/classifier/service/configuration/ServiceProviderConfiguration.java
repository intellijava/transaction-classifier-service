package se.sbab.transaction.classifier.service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

public class ServiceProviderConfiguration {

  @Value("${service.version}")
  private String version;

  @Value("${owner.email}")
  private String emailOwner;

  @Value("${owner.name}")
  private String nameOwner;

  @Value("${spring.application.name}")
  private String applicationName;
  @Bean
  public OpenAPI apiDoc(Environment env) {
    String activeProfiles = String.join(",", env.getActiveProfiles());
    Contact contact = new Contact().email(emailOwner).name(nameOwner);
    Info info = new Info().title(applicationName).version(version + " - " + activeProfiles).contact(contact);

    return new OpenAPI().info(info);
  }

}
