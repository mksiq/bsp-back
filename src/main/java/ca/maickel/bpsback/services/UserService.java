package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.UserDTO;
import ca.maickel.bpsback.repositories.UserRepository;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepository repo;

  private final PhotoService photoService;

  public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository repo, PhotoService photoService) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.repo = repo;
    this.photoService = photoService;
  }

  public User find(Integer id) {
    Optional<User> obj = repo.findById(id);
    return obj.orElseThrow(
        () ->
            new ObjectNotFoundException(
                "Object not Found: " + id + ", Type: " + User.class.getName()));
  }

  public List<User> findAll() {
    return repo.findAll();
  }

  @Transactional
  public User insert(User obj) {
    obj.setId(null);
    obj = repo.save(obj);
    return obj;
  }

  public User fromDTO(UserDTO objDTO) {
    User user =
        new User(
            null,
            objDTO.getUserName(),
            objDTO.getEmail(),
            bCryptPasswordEncoder.encode(objDTO.getPassword()),
            objDTO.getSignUpDate());
    return user;
  }

  public User update(User obj) {
    User newObj = find(obj.getId());
    updateData(newObj, obj);
    return repo.save(newObj);
  }
  /** Allows only update for userName, email, and password. */
  private void updateData(User newObj, User obj) {
    newObj.setUserName(obj.getUserName());
    newObj.setEmail(obj.getEmail());
    newObj.setPassword(bCryptPasswordEncoder.encode(obj.getPassword()));
  }

  public void delete(Integer id) {
    repo.deleteById(id);
  }
}
