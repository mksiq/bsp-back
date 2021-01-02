package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.UserDTO;
import ca.maickel.bpsback.enums.Profile;
import ca.maickel.bpsback.repositories.UserRepository;
import ca.maickel.bpsback.security.UserSecurity;
import ca.maickel.bpsback.services.exceptions.AuthorizationException;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepository repo;
  private final EmailService emailService;
  private final PhotoServiceImpl photoServiceImpl;

  /** return logged and authenticated user */
  public static UserSecurity authenticated() {
    try {
      return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    } catch (Exception e) {
      return null;
    }
  }

  public UserService(
          BCryptPasswordEncoder bCryptPasswordEncoder,
          UserRepository repo,
          EmailService emailService,
          PhotoServiceImpl photoServiceImpl) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.repo = repo;
    this.emailService = emailService;
    this.photoServiceImpl = photoServiceImpl;
  }

  /** Only himself can get his own data */
  public User find(Integer id) {
    UserSecurity user = authenticated();
    if (user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
      throw new AuthorizationException("Access not allowed");
    }
    Optional<User> obj = repo.findById(id);
    return obj.orElseThrow(
        () ->
            new ObjectNotFoundException(
                "Object not Found: " + id + ", Type: " + User.class.getName()));
  }

  /** Any logged user can get other usernames by id */
  public String findUsernameById(Integer id) {
    UserSecurity user = authenticated();
    if (user == null) {
      throw new AuthorizationException("Access not allowed");
    }
    Optional<User> obj = repo.findById(id);
    if (!obj.isPresent()) {
      throw new ObjectNotFoundException(
          "Object not Found: " + id + ", Type: " + User.class.getName());
    } else {
      return obj.get().getUserName();
    }
  }

  public User findByEmail(String email) {
    UserSecurity user = UserService.authenticated();
    if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
      throw new AuthorizationException("Access not allowed");
    }
    User obj = find(user.getId());
    if (obj == null) {
      throw new ObjectNotFoundException(
          "Object not Found: " + user.getId() + ", Type: " + User.class.getName());
    }
    return obj;
  }

  public List<User> findAll() {
    return repo.findAll();
  }

  @Transactional
  public User insert(User obj) {
    obj.setId(null);
    obj = repo.save(obj);
    emailService.sendSignupConfirmationEmail(obj);
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
