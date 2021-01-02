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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface PhotoService {

  public Photo find(Integer id);

  public List<Photo> findAll();

  public List<Photo> findAllByUser(User user);

  public Photo insert(Photo obj);

  public Photo update(Photo obj);

  public void delete(Integer id);

  public URI uploadPhoto(MultipartFile file, Integer id);

  public Photo fromDTO(NewPhotoDTO objDTO);
}
