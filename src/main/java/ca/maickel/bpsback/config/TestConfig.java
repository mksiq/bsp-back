package ca.maickel.bpsback.config;

import ca.maickel.bpsback.services.DBService;
import ca.maickel.bpsback.services.EmailService;
import ca.maickel.bpsback.services.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

  private final DBService dbService;

  public TestConfig(DBService dbService) {
    this.dbService = dbService;
  }

  @Bean
  public boolean instantiateDatabase() {
    dbService.instantiateTestDabatase();
    return true;
  }

  @Bean
  public EmailService emailService() {
    return new SmtpEmailService();
  }
}
