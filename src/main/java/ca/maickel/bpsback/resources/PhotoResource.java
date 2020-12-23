package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.services.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

        return ResponseEntity.ok().body(obj);
    }
}
