package ca.maickel.bpsback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final Environment environment;

  /** These endpoints are free to access */
  private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
  /** These GET endpoints are free to access */
  private static final String[] PUBLIC_MATCHERS_GET = {"/photos/**", "/tags/**"};

  public SecurityConfig(Environment environment) {
    this.environment = environment;
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
        .antMatchers(PUBLIC_MATCHERS)
        .permitAll()
        .anyRequest()
        .authenticated();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
