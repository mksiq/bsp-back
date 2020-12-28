package ca.maickel.bpsback.security;

import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.enums.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSecurity implements UserDetails {

  private static final long serialVersionUID = 1L;
  private Integer id;
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserSecurity(Integer id, String email, String password, Set<Profile> roles) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = roles.stream().map( role -> new SimpleGrantedAuthority(role.getDescription())).collect(Collectors.toSet());
  }

  public Integer getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
