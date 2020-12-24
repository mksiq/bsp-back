package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.dto.PhotoDTO;
import ca.maickel.bpsback.dto.UserDTO;
import ca.maickel.bpsback.services.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/photos")
public class PhotoResource {

    private final PhotoService service;

    public PhotoResource(PhotoService service) {
        this.service = service;
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id){
        Photo obj = service.find(id);
        PhotoDTO objDTO = new PhotoDTO(obj);
        return ResponseEntity.ok().body(objDTO);
    }

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Photo> list = service.findAll();
        List<PhotoDTO> listDTO = list.stream().map(photo -> new PhotoDTO(photo)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
}
