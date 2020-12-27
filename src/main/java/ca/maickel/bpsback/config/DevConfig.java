package ca.maickel.bpsback.config;

import ca.maickel.bpsback.services.DBService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

  private final DBService dbService;

  public DevConfig(DBService dbService) {
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
}
