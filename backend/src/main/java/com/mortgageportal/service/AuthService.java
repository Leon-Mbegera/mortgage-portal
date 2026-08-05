package com.mortgageportal.service;

import com.mortgageportal.dto.auth.RegisterRequest;
import com.mortgageportal.dto.auth.LoginRequest;
import com.mortgageportal.dto.auth.AuthResponse;
import com.mortgageportal.entity.Role;
import com.mortgageportal.entity.User;
import com.mortgageportal.repository.RoleRepository;
import com.mortgageportal.repository.UserRepository;
import com.mortgageportal.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }
  
  @Transactional
  public AuthResponse register(RegisterRequest request) {

    if (userRepository.existsByEmail(request.email())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already taken");
    }

    Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new IllegalStateException("Default role USER doesn't exist"));

    User user = User.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .role(userRole)
                    .build();

    userRepository.save(user);

    return buildAuthResponse(user);
  }

  public AuthResponse login(LoginRequest request) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password())
      );

    } catch (BadCredentialsException e){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    User user = userRepository.findByEmail(request.email())
                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

    return buildAuthResponse(user);
  }

  private AuthResponse buildAuthResponse(User user) {
    String token = jwtService.generateToken(user.getEmail(), Map.of("role", user.getRole().getName()));

    return new AuthResponse(
      token,
      "Bearer",
      user.getId(),
      user.getFirstName(),
      user.getLastName(),
      user.getEmail(),
      user.getRole().getName()
    );
  }
}
