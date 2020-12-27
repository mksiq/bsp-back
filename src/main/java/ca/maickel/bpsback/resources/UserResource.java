package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.UserDTO;
import ca.maickel.bpsback.services.PhotoService;
import ca.maickel.bpsback.services.TransactionService;
import ca.maickel.bpsback.services.UserService;
import org.springframework.http.ResponseEntity;
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
    obj.setBoughtTransactions(boughtTransactionsList);
    obj.setSoldTransactions(soldTransactionsList);

    for (int i = 0; i < obj.getBoughtTransactions().size(); ++i) {
      obj.getBoughtTransactions().get(i).setBuyer(null);
    }
    for (int i = 0; i < obj.getSoldTransactions().size(); ++i) {
      obj.getSoldTransactions().get(i).setBuyer(null);
    }

    return ResponseEntity.ok().body(obj);
  }

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
