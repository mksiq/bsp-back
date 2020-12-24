package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.dto.TransactionDTO;
import ca.maickel.bpsback.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/transactions")
public class TransactionResource {

    private final TransactionService service;

    public TransactionResource(TransactionService service) {
        this.service = service;
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id){
        Transaction obj = service.find(id);
        TransactionDTO objDTO = new TransactionDTO(obj);
        return ResponseEntity.ok().body(objDTO);
    }

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Transaction> list = service.findAll();
        List<TransactionDTO> listDTO = list.stream().map(obj -> new TransactionDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
}
