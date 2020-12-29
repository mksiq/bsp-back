package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.NewPhotoDTO;
import ca.maickel.bpsback.enums.Profile;
import ca.maickel.bpsback.repositories.PhotoRepository;
import ca.maickel.bpsback.security.UserSecurity;
import ca.maickel.bpsback.services.exceptions.AuthorizationException;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhotoService {

  private final PhotoRepository repo;
  private final S3Service s3Service;
  private final ImageService imageService;

  public PhotoService(PhotoRepository repo, S3Service s3Service, ImageService imageService) {
    this.repo = repo;
    this.s3Service = s3Service;
    this.imageService = imageService;
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

  /** only a logged user may insert a photo owned by himself */
  public Photo insert(Photo obj) {
    obj.setId(null);
    UserSecurity user = UserService.authenticated();
    if (user == null
        || !user.hasRole(Profile.ADMIN) && !obj.getUser().getId().equals(user.getId())) {
      throw new AuthorizationException("Access not allowed");
    }
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
    UserSecurity user = UserService.authenticated();
    if (user == null
        || !user.hasRole(Profile.ADMIN) && !obj.getUser().getId().equals(user.getId())) {
      throw new AuthorizationException("Access not allowed");
    }
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

  /**
   * Uploads photo right after inserting a photo object. Converts to jpg and renames the file into a
   * photo id then updates photo filename to generated URI
   */
  public URI uploadPhoto(MultipartFile file, Integer id) {
    UserSecurity user = UserService.authenticated();
    Photo photo = find(id);
    if (user == null || !user.hasRole(Profile.ADMIN) && !photo.getUser().getId().equals(id)) {
      throw new AuthorizationException("Access not allowed");
    }
    String fileName = "photo_" + id + ".jpg";
    BufferedImage jpgImg = imageService.getJpgImgFromFile(file);
    URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImg, "jpg"), fileName, "image");
    photo.setFileName(uri.toString());
    repo.save(photo);
    return uri;
  }
}
