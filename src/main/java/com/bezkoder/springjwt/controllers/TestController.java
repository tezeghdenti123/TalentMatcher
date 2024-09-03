package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.CommercialeRepository;
import com.bezkoder.springjwt.repository.ConsultantRepository;
import com.bezkoder.springjwt.repository.DevelopperRepository;
import com.bezkoder.springjwt.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @Autowired
  private ConsultantRepository consultantRepository;
  @Autowired
  private CommercialeRepository commercialeRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private DevelopperRepository developperRepository;
  @GetMapping("/all")
  @Transactional
  public List<Consultant> allAccess() {
    Consultant consultant=new Consultant();
    consultant.setTitle("hiiii");
    Role role=roleRepository.getReferenceById(1L);
    //Set<Role>listRole=  new LinkedHashSet<>();
    Langues langues =new Langues();
    langues.setName("Arabe");
    consultant.addLanguage(langues);
    //listRole.add(role);

    consultantRepository.save(consultant);
    List<Consultant> consultantlist=consultantRepository.findAll();
    return consultantlist;
  }
  /*@PostMapping("/save")
  @Transactional
  public List<Developper> saveConsultants(@RequestBody Developper developper) {
    Consultant consultant=new Consultant();
    consultant.setTitle("hiiii");
    Role role=roleRepository.getReferenceById(1L);
    //Set<Role>listRole=  new LinkedHashSet<>();
    Language language=new Language();
    language.setName("Arabe");
    consultant.addLanguage(language);
    //listRole.add(role);
    developperRepository.save(developper);
    List<Developper> consultantlist=developperRepository.findAll();
    return consultantlist;
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('RH')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
  @GetMapping("/getCommerciales")
  @PreAuthorize("hasRole('ADMIN')")
  public List<Commerciale> getListCommerciale() {
    List<Commerciale>commercialeList=commercialeRepository.findAll();
    return commercialeList;
  }
  @GetMapping("/getDevs")
  @PreAuthorize("hasRole('ADMIN')")
  public List<Developper> getDevelopperList() {
    List<Developper>developperList=developperRepository.findAll();
    return developperList;
  }*/
}
