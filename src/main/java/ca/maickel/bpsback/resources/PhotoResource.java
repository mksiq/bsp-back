package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.dto.NewPhotoDTO;
import ca.maickel.bpsback.dto.PhotoDTO;
import ca.maickel.bpsback.services.PhotoService;
import ca.maickel.bpsback.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/photos")
public class PhotoResource {

  private final PhotoService service;
  private final TagService tagService;

  public PhotoResource(PhotoService service, TagService tags) {
    this.service = service;
    this.tagService = tags;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> find(@PathVariable Integer id) {
    Photo obj = service.find(id);
    PhotoDTO objDTO = new PhotoDTO(obj);
    return ResponseEntity.ok().body(objDTO);
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<?> findAll() {
    List<Photo> list = service.findAll();
    List<PhotoDTO> listDTO =
        list.stream().map(photo -> new PhotoDTO(photo)).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDTO);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Void> insert(@RequestBody NewPhotoDTO objDTO) {
    System.out.println("** USER ID**");
    System.out.println(objDTO.getUser().getId());
    Photo obj = service.fromDTO(objDTO);
    obj.setId(null);
    obj.setTags(tagService.insertNTags(obj.getTags()));
    obj = service.insert(obj);
    System.out.println("** USER ID After insertion**");
    System.out.println(obj.getUser().getId());
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(obj.getId())
            .toUri();
    return ResponseEntity.created(uri).build();
  }
}
