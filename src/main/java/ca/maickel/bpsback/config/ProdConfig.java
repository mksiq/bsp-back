package ca.maickel.bpsback.config;

import ca.maickel.bpsback.services.DBService;
import ca.maickel.bpsback.services.EmailService;
import ca.maickel.bpsback.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {

  private final DBService dbService;

  public ProdConfig(DBService dbService) {
    this.dbService = dbService;
  }

  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String strategy;

  @Bean
  public boolean instantiateDatabase() {
    if (strategy.equals("create")) {
      dbService.instantiateTestDabatase();
      return true;
    }
    return false;
  }

  @Bean
  public EmailService emailService() {
    return new SmtpEmailService();
  }
}
