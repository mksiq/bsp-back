package ca.maickel.bpsback;

import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class BpsBackApplication implements CommandLineRunner {

	private final TagRepository tagRepository;

	public BpsBackApplication(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BpsBackApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Tag t1 = new Tag(null,"Banana");
		Tag t2 = new Tag(null,"Fruit");

		tagRepository.saveAll(Arrays.asList(t1,t2));

	}
}
