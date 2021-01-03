package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.NewPhotoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

public interface PhotoService {

  public Photo find(Integer id);

  public List<Photo> findAll();

  public List<Photo> findAllByUser(User user);

  public Photo insert(Photo obj);

  public Photo update(Photo obj);

  public void delete(Integer id);

  public URI uploadPhoto(MultipartFile file, Integer id);

  public Photo fromDTO(NewPhotoDTO objDTO);

  public Photo increaseDownloads(Photo obj);
}
