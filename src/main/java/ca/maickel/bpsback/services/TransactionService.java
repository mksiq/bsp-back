package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.NewTransactionDTO;
import ca.maickel.bpsback.enums.Profile;
import ca.maickel.bpsback.repositories.TransactionRepository;
import ca.maickel.bpsback.security.UserSecurity;
import ca.maickel.bpsback.services.exceptions.AuthorizationException;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

  private final TransactionRepository repo;
  private final UserService userService;
  private final PhotoService photoService;
  private final EmailService emailService;

  public TransactionService(TransactionRepository repo, UserService userService, PhotoService photoService, EmailService emailService) {
    this.repo = repo;
    this.userService = userService;
    this.photoService = photoService;
    this.emailService = emailService;
  }

  public Transaction find(Integer id) {
    Optional<Transaction> obj = repo.findById(id);
    return obj.orElseThrow(
        () ->
            new ObjectNotFoundException(
                "Object not Found: " + id + ", Type: " + Transaction.class.getName()));
  }

  public List<Transaction> findAll() {
    return repo.findAll();
  }

  /** User may only look for his buyer transactions */
  public List<Transaction> findAllByBuyer(User buyer) {
    UserSecurity user = UserService.authenticated();
    if (user == null || !user.hasRole(Profile.ADMIN) && !buyer.getId().equals(user.getId())) {
      throw new AuthorizationException("Access not allowed");
    }
    return repo.findAllByBuyer(buyer);
  }
  /** User may only look for his seller transactions */
  public List<Transaction> findAllBySeller(User seller) {
    UserSecurity user = UserService.authenticated();
    if (user == null || !user.hasRole(Profile.ADMIN) && !seller.getId().equals(user.getId())) {
      throw new AuthorizationException("Access not allowed");
    }
    return repo.findAllBySeller(seller);
  }

  public Transaction fromDTO(NewTransactionDTO objDTO) {
    return new Transaction(objDTO);
  }

  /** User must be logged in and the transaction buyer must match him */
  public Transaction insert(Transaction obj) {
    UserSecurity user = UserService.authenticated();
    if (user == null) {
      throw new AuthorizationException("Access not allowed");
    }
    obj.setId(null);
    obj.setBuyer(new User(user));
    User seller = new User();
    seller.setId(obj.getPhoto().getUser().getId());
    seller.setUserName(userService.findUsernameById(obj.getPhoto().getUser().getId()));
    obj.setSeller(seller);
    obj.setPhoto(photoService.find(obj.getPhoto().getId()));
    obj = repo.save(obj);
    emailService.sendTransactionConfirmationEmail(obj);
    return obj;
  }

  public void delete(Integer id) {
    repo.deleteById(id);
  }
}
