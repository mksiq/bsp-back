package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.dto.TagDTO;
import ca.maickel.bpsback.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/tags")
public class TagResource {

    private final TagService service;

    public TagResource(TagService service) {
        this.service = service;
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id){
        Tag obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Tag> list = service.findAll();
        List<TagDTO> listDTO = list.stream().map(obj -> new TagDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
}
