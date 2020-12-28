package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.NewPhotoDTO;
import ca.maickel.bpsback.repositories.PhotoRepository;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhotoService {

  private final PhotoRepository repo;

  public PhotoService(PhotoRepository repo) {
    this.repo = repo;
  }

  public Photo find(Integer id) {
    Optional<Photo> obj = repo.findById(id);
    return obj.orElseThrow(
        () ->
            new ObjectNotFoundException(
                "Object not Found: " + id + ", Type: " + User.class.getName()));
  }

  public List<Photo> findAll() {
    return repo.findAll();
  }

  /** Returns photo by the buyer and removes tag and user to avoid Json recursion */
  public List<Photo> findAllByUser(User user) {
    List<Photo> cleared = repo.findAllByUser(user);
    cleared =
        cleared.stream()
            .map(
                photo -> {
                  Set<Tag> empty = new HashSet<>();
                  photo.setTags(empty);
                  photo.setUser(null);
                  return photo;
                })
            .collect(Collectors.toList());
    return cleared;
  }

  public Photo fromDTO(NewPhotoDTO objDTO) {
    return new Photo(objDTO);
  }

  public Photo insert(Photo obj) {
    obj.setId(null);
    obj = repo.save(obj);
    return obj;
  }

  /**
   * Everytime a transaction is inserted this function is called to update the this download count
   */
  public Photo increaseDownloads(Photo obj) {
    obj.setDownloads(obj.getDownloads() + 1);
    obj = repo.save(obj);
    return obj;
  }

  public Photo update(Photo obj) {
    Photo newObj = find(obj.getId());
    updateData(newObj, obj);
    return repo.save(newObj);
  }
  /** Allows only update of title, price, and tags */
  private void updateData(Photo newObj, Photo obj) {
    newObj.setTitle(obj.getTitle());
    newObj.setPrice(obj.getPrice());
    newObj.setTags(obj.getTags());
  }
}
