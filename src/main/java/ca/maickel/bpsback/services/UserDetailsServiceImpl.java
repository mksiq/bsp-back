package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.repositories.UserRepository;
import ca.maickel.bpsback.security.UserSecurity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    }
    return new UserSecurity(user.getId(), user.getEmail(), user.getPassword(), user.getProfiles());
  }
}
