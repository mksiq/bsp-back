package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.UserDTO;
import ca.maickel.bpsback.security.UserSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public interface UserService {

  /** return logged and authenticated user */
  public static UserSecurity authenticated() {
    try {
      return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    } catch (Exception e) {
      return null;
    }
  }

  public User find(Integer id);

  public String findUsernameById(Integer id);

  public User findByEmail(String email);

  public List<User> findAll();

  public User insert(User obj);

  public User fromDTO(UserDTO objDTO);

  public User update(User obj);

  public void delete(Integer id);
}
