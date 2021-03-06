package ca.maickel.bpsback.config;

import ca.maickel.bpsback.security.JWTAuthenticationFilter;
import ca.maickel.bpsback.security.JWTAuthorizationFilter;
import ca.maickel.bpsback.security.JWTUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final Environment environment;
  private final JWTUtil jwtUtil;

  private final UserDetailsService userDetailsService;
  /** These endpoints are free to access for testing */
  private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
  /** These GET endpoints are free to access for non logged users */
  private static final String[] PUBLIC_MATCHERS_GET = {"/photos/**", "/tags/**"};
  /** These POST endpoints are free to access. A non logged user may register himself */
  private static final String[] PUBLIC_MATCHERS_POST = {"/users/**"};

  public SecurityConfig(
      Environment environment,
      JWTUtil jwtUtil,
      @Qualifier("UserDetailsServiceImpl") UserDetailsService userDetailsService) {
    this.environment = environment;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    /** Allows h2-console */
    if (Arrays.asList(environment.getActiveProfiles()).contains("test"))
      http.headers().frameOptions().disable();

    /**
     * Allows cross origin and as the server is stateless (tokens) no need to keep track of sessions
     */
    http.cors().and().csrf().disable();
    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET)
        .permitAll()
        .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST)
        .permitAll()
        .antMatchers(PUBLIC_MATCHERS)
        .permitAll()
        .anyRequest()
        .authenticated();
    http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
    http.addFilter(
        new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    corsConfiguration.setAllowedMethods(Arrays.asList("POST", "PUT", "DELETE", "GET","OPTIONS"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
