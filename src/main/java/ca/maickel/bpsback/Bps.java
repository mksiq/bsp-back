package ca.maickel.bpsback;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Bps implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(Bps.class, args);
  }

  @Override
  public void run(String... args) throws Exception {}
}
