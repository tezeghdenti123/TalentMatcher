package com.bezkoder.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repositories.*;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.UserRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;
  @Autowired
  CommercialeRepository commercialeRepository;
  @Autowired
  GesionnaireRHRepository gestionnaireRHRepository;
  @Autowired
  PasswordEncoder encoder;
  @Autowired
  DevelopperRepository developperRepository;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }


  @PostMapping("/admin")
  public ResponseEntity<?> saveAdministrateur(@Valid @RequestBody UserRequest userRequest) {
    if (userRepository.existsByUsername(userRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(userRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(userRequest.getUsername(),
            userRequest.getEmail(),
            encoder.encode(userRequest.getPassword()));
    user.setName(userRequest.getName());
    user.setPhone(userRequest.getPhone());

    Set<String> strRoles = userRequest.getRole();
    Set<Role> roles = new HashSet<>();
    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(adminRole);

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/commerciale")
  public ResponseEntity<?> saveCommerciale(@Valid @RequestBody Commerciale commerciale) {
    if (userRepository.existsByUsername(commerciale.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(commerciale.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(commerciale.getUsername(),
            commerciale.getEmail(),
            encoder.encode(commerciale.getPassword()));
    user.setName(commerciale.getName());
    user.setPhone(commerciale.getPhone());

    Set<Role> roles = new HashSet<>();
    Role adminRole = roleRepository.findByName(ERole.ROLE_COMMERCIALE)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(adminRole);

    //user.setRoles(roles);
    commerciale.setRoles(roles);
    commerciale.setPassword(encoder.encode(commerciale.getPassword()));
    //userRepository.save(user);
    commercialeRepository.save(commerciale);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  @PostMapping("/developper")
  public ResponseEntity<?> saveDevelopper(@Valid @RequestBody Developper developper) {
    if (userRepository.existsByUsername(developper.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(developper.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(developper.getUsername(),
            developper.getEmail(),
            encoder.encode(developper.getPassword()));
    user.setName(developper.getName());
    user.setPhone(developper.getPhone());

    Set<Role> roles = new HashSet<>();
    Role adminRole = roleRepository.findByName(ERole.ROLE_DEVELOPPER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(adminRole);

    //user.setRoles(roles);
    developper.setRoles(roles);
    developper.setPassword(encoder.encode(developper.getPassword()));
    //userRepository.save(user);
    developperRepository.save(developper);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  @PostMapping("/gestionnaireRH")
  public ResponseEntity<?> saveGestionnaire(@Valid @RequestBody GestionnaireRH gestionnaireRH) {
    if (userRepository.existsByUsername(gestionnaireRH.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(gestionnaireRH.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User(gestionnaireRH.getUsername(),
            gestionnaireRH.getEmail(),
            encoder.encode(gestionnaireRH.getPassword()));
    user.setName(gestionnaireRH.getName());
    user.setPhone(gestionnaireRH.getPhone());

    Set<Role> roles = new HashSet<>();
    Role adminRole = roleRepository.findByName(ERole.ROLE_GESTIONNAIRE)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(adminRole);

    gestionnaireRH.setRoles(roles);
    gestionnaireRH.setPassword(encoder.encode(gestionnaireRH.getPassword()));
    gestionnaireRHRepository.save(gestionnaireRH);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
