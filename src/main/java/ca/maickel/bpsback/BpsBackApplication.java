package ca.maickel.bpsback;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.repositories.PhotoRepository;
import ca.maickel.bpsback.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class BpsBackApplication implements CommandLineRunner {

	private final TagRepository tagRepository;

	private final PhotoRepository photoRepository;

	public BpsBackApplication(TagRepository tagRepository, PhotoRepository photoRepository) {
		this.tagRepository = tagRepository;
		this.photoRepository = photoRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BpsBackApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Tag t1 = new Tag(null,"Banana");
		Tag t2 = new Tag(null,"Fruit");

//		fileName, Integer width, Integer height, Double price, LocalDate date, String title, Integer downloads)
		Photo p1 = new Photo(null, "banana.jpg", 800, 600, 5.99, LocalDate.now(), "Lonely Banana", 1);

		t1.getPhotos().addAll(Arrays.asList(p1));
		t2.getPhotos().addAll(Arrays.asList(p1));

		p1.getTags().addAll(Arrays.asList(t1,t2));
		tagRepository.saveAll(Arrays.asList(t1,t2));
		photoRepository.saveAll(Arrays.asList(p1));

	}
}
