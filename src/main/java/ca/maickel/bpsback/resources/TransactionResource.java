package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.dto.NewTransactionDTO;
import ca.maickel.bpsback.dto.TransactionDTO;
import ca.maickel.bpsback.services.PhotoService;
import ca.maickel.bpsback.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/transactions")
public class TransactionResource {

    private final TransactionService service;
    private final PhotoService photoService;

    public TransactionResource(TransactionService service, PhotoService photoService) {
        this.service = service;
        this.photoService = photoService;
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody NewTransactionDTO objDTO){
        objDTO.setPhoto(photoService.find(objDTO.getPhoto().getId()));
        photoService.increaseDownloads(objDTO.getPhoto());
        Transaction obj = service.fromDTO(objDTO);
        obj = service.insert(obj);
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .path("/{id}")
                        .buildAndExpand(obj.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }
}
