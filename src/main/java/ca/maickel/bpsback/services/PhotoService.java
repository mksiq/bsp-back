package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.dto.NewPhotoDTO;
import ca.maickel.bpsback.dto.PhotoDTO;
import ca.maickel.bpsback.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
