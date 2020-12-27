package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.dto.TagDTO;
import ca.maickel.bpsback.dto.TagDTONoPhotos;
import ca.maickel.bpsback.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
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
        TagDTO objDTO = new TagDTO(obj);
        return ResponseEntity.ok().body(objDTO);
    }
    @RequestMapping(value="/tag={tag}", method= RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable String tag){
        Tag obj = service.findByTag(tag);
        TagDTO objDTO = new TagDTO(obj);

        return ResponseEntity.ok().body(objDTO);
    }

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Tag> list = service.findAll();
        List<TagDTONoPhotos> listDTO = list.stream().map(obj -> new TagDTONoPhotos(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody TagDTO objDTO){
        Tag obj = service.fromDTO(objDTO);
        obj.setId(null);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
