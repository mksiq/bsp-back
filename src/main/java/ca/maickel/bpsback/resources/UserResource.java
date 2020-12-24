package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.TransactionDTO;
import ca.maickel.bpsback.dto.UserDTO;
import ca.maickel.bpsback.services.TransactionService;
import ca.maickel.bpsback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/users")
public class UserResource {

    private final UserService service;
    private final TransactionService transactionService;

    public UserResource(UserService service, TransactionService transactionService) {
        this.service = service;
        this.transactionService = transactionService;
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id){
        User obj = service.find(id);

        List<Transaction> boughtTransactionsList = transactionService.findAllByBuyer(obj);
        List<Transaction> soldTransactionsList = transactionService.findAllByBuyer(obj);

        for(int i = 0 ; i < obj.getBoughtTransactions().size(); ++i){
            obj.getBoughtTransactions().get(i).setBuyer(null);
        }
        for(int i = 0 ; i < obj.getSoldTransactions().size(); ++i){
            obj.getSoldTransactions().get(i).setBuyer(null);
        }

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<User> list = service.findAll();
        List<UserDTO> listDTO = list.stream().map( obj -> new UserDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody UserDTO objDTO){
        User obj = service.fromDTO(objDTO);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
