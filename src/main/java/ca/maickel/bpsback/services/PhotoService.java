package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.NewPhotoDTO;
import ca.maickel.bpsback.dto.PhotoDTO;
import ca.maickel.bpsback.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhotoService {

  private final PhotoRepository repo;

  public PhotoService(PhotoRepository repo) {
    this.repo = repo;
  }

  public Photo find(Integer id) {
    Optional<Photo> obj = repo.findById(id);
    return obj.orElse(null);
  }

  public List<Photo> findAll() {
    return repo.findAll();
  }

  public List<Photo> findAllByUser(User user) {
    List<Photo> cleared = repo.findAllByUser(user);
    cleared = cleared.stream().map( photo -> {
            Set<Tag> empty = new HashSet<>();
            photo.setTags(empty);
            photo.setUser(null);
            return photo;
        }).collect(Collectors.toList());
    return cleared;
  }

  public Photo fromDTO(PhotoDTO objDTO) {
    return new Photo(objDTO);
  }

  public Photo fromDTO(NewPhotoDTO objDTO) {
    return new Photo(objDTO);
  }

  public Photo insert(Photo obj) {
    obj.setId(null);
    obj = repo.save(obj);
    return obj;
  }
}
