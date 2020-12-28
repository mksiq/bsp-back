package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.security.JWTUtil;
import ca.maickel.bpsback.security.UserSecurity;
import ca.maickel.bpsback.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

  private final JWTUtil jwtUtil;

  public AuthResource(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
  public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
    UserSecurity user = UserService.authenticated();
    String token = jwtUtil.generateToken(user.getUsername());
    response.addHeader("Authorization", "Bearer " + token);
    response.addHeader("access-control-expose-headers", "Authorization");
    return ResponseEntity.noContent().build();
  }
}
