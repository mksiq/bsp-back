package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.UserDTO;
import ca.maickel.bpsback.services.PhotoService;
import ca.maickel.bpsback.services.TransactionService;
import ca.maickel.bpsback.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

  private final UserService service;
  private final PhotoService photoService;
  private final TransactionService transactionService;

  public UserResource(
          UserService service, PhotoService photoService, TransactionService transactionService) {
    this.service = service;
    this.photoService = photoService;
    this.transactionService = transactionService;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> find(@PathVariable Integer id) {
    User obj = service.find(id);
    List<Photo> ownedPhotos = photoService.findAllByUser(obj);
    obj.setPhotos(ownedPhotos);
    List<Transaction> boughtTransactionsList = transactionService.findAllByBuyer(obj);
    List<Transaction> soldTransactionsList = transactionService.findAllBySeller(obj);
//    boughtTransactionsList.forEach(transaction -> transaction.setBuyer(null));
//    soldTransactionsList.forEach(transaction -> transaction.setBuyer(null));
    obj.setBoughtTransactions(boughtTransactionsList);
    obj.setSoldTransactions(soldTransactionsList);
    return ResponseEntity.ok().body(obj);
  }

  @RequestMapping(value = "/email", method = RequestMethod.GET)
  public ResponseEntity<?> find(@RequestParam(value="value") String email) {
    System.out.println("Test");
    User obj = service.findByEmail(email);
    UserDTO user = null;
    if(obj != null){
      user = new UserDTO(obj);
      user.setPassword("");
    }
    return ResponseEntity.ok().body(user);
  }

  /** Only admins may get all users */
  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<?> findAll() {
    List<User> list = service.findAll();
    List<UserDTO> listDTO = list.stream().map(obj -> new UserDTO(obj)).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDTO);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Void> insert(@RequestBody UserDTO objDTO) {
    User obj = service.fromDTO(objDTO);
    obj.setId(null);
    obj.setSignUpDate(LocalDate.now());
    obj = service.insert(obj);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(obj.getId())
            .toUri();
    return ResponseEntity.created(uri).build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Void> update(@RequestBody UserDTO objDTO, @PathVariable Integer id) {
    User obj = service.fromDTO(objDTO);
    obj.setId(id);
    obj = service.update(obj);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("")
            .buildAndExpand(obj.getId())
            .toUri();
    return ResponseEntity.created(uri).build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
