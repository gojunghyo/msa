package com.example.userservice.security;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

  private final UserService userService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final Environment env;
  private final ObjectPostProcessor<Object> objectPostProcessor;

  final String[] whiteList = {"/users/**", "/**"};
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.csrf().disable();
    http.headers().frameOptions().disable();
    http.authorizeHttpRequests((authorize -> {
      try {
        authorize.requestMatchers(whiteList).permitAll()
            .requestMatchers(new IpAddressMatcher("127.0.0.1")).permitAll()
            .and()
            .addFilter(getAuthenticationFilter());
      }catch (Exception e){
        e.printStackTrace();
      }
    }));
    return http.build();
  }



  // select pwd from users wehere email=?
  // db_pwd(encrypted) == input_pwd(encrypted)
  public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder); // bean 만들어졌으니 주입만
    return auth.build();
  }

  private AuthenticationFilter getAuthenticationFilter() throws Exception{
    AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
    AuthenticationFilter authenticationFilter =
        new AuthenticationFilter(authenticationManager(builder), userService, env);
    return authenticationFilter;
  }
}
