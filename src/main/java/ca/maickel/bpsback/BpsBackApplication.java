package ca.maickel.bpsback;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.repositories.PhotoRepository;
import ca.maickel.bpsback.repositories.TagRepository;
import ca.maickel.bpsback.repositories.TransactionRepository;
import ca.maickel.bpsback.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class BpsBackApplication implements CommandLineRunner {

	private final TagRepository tagRepository;

	private final PhotoRepository photoRepository;

	private final UserRepository userRepository;

	private final TransactionRepository transactionRepository;

	public BpsBackApplication(TagRepository tagRepository, PhotoRepository photoRepository,
							  UserRepository userRepository, TransactionRepository transactionRepository) {
		this.tagRepository = tagRepository;
		this.photoRepository = photoRepository;
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BpsBackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tag t1 = new Tag(null,"Banana");
		Tag t2 = new Tag(null,"Fruit");

// public User(Integer id, String userName, String email, String password, LocalDate signUpDate) {
		User u1 = new User(null, "msiqueira", "mcksiq@gmail.com", "", LocalDate.now());
		User u2 = new User(null, "someone", "s@gmail.com", "", LocalDate.now());
		User u3 = new User(null, "john", "j@gmail.com", "", LocalDate.now());
//		fileName, Integer width, Integer height, Double price, LocalDate date, String title, Integer downloads)
		Photo p1 = new Photo(null, "banana.jpg", 800, 600, 5.99, LocalDate.now(), "Lonely Banana", 4, u1);
		Photo p2 = new Photo(null, "kiwi.jpg", 800, 600, 3.99, LocalDate.now(), "Lonely Kiwi", 1, u1);
// public Transaction(Integer id, LocalDate date, Double listPrice, User buyer, User seller, Photo photo) {
		Transaction tr1 = new Transaction(null, LocalDate.now(), p1.getPrice(), u2, u1, p1);
	//
		p1.setUser(u1);
		u2.getBoughtTransactions().addAll(Arrays.asList(tr1));
		u1.getSoldTransactions().addAll(Arrays.asList(tr1));
	//	t1.getPhotos().addAll(Arrays.asList(p1));
	//	t2.getPhotos().addAll(Arrays.asList(p1));


		p1.getTags().addAll(Arrays.asList(t1,t2));
//
		userRepository.saveAll(Arrays.asList(u1,u2));
//		photoRepository.saveAll(Arrays.asList(p1));
//		tagRepository.saveAll(Arrays.asList(t1,t2));
//		u1.getPhotos().addAll(Arrays.asList(p1));
//		transactionRepository.saveAll(Arrays.asList(tr1));

	}
}
