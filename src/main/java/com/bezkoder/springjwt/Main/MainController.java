package com.bezkoder.springjwt.Main;

import java.util.*;
import java.util.stream.Collectors;

import com.bezkoder.springjwt.DTO.ConsultantDTO;
import com.bezkoder.springjwt.DTO.ExperienceDTO;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.UserRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/main")
public class MainController {
    @Autowired
    private MainService mainService;
    @Autowired
    private ConsultantRepository consultantRepository;
    @Autowired
    private CommercialeRepository commercialeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DevelopperRepository developperRepository;
    @Autowired
    private GesionnaireRHRepository gesionnaireRHRepository;
    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private FormationsRepository formationsRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SkillRepository skillRepository;

    @PostMapping("/save")
    @Transactional
    public List<Developper> saveConsultants(@RequestBody Developper developper) {
    /*Consultant consultant=new Consultant();
    consultant.setTitle("hiiii");
    Role role=roleRepository.getReferenceById(1L);
    //Set<Role>listRole=  new LinkedHashSet<>();
    Language language=new Language();
    language.setName("Arabe");
    consultant.addLanguage(language);
    //listRole.add(role);*/
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
    public List<Commerciale> getListCommerciale() {
        List<Commerciale>commercialeList=commercialeRepository.findAll();
        return commercialeList;
    }
    @GetMapping("/getGestionnaires")
    public List<GestionnaireRH>getGestionnaire(){
        List<GestionnaireRH>gestionnaireRHList=gesionnaireRHRepository.findAll();
        return gestionnaireRHList;
    }
    @GetMapping("/getDevs")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Developper> getDevelopperList() {
        List<Developper>developperList=developperRepository.findAll();
        return developperList;
    }
    @GetMapping("/getConsByComId")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMMERCIALE')")
    public List<ConsultantDTO> getConsultantByComId(@RequestParam Long commercialeId){

        Commerciale commerciale=commercialeRepository.findById(commercialeId).orElseThrow(() -> new RuntimeException("Commerciale not found"));
        List<Consultant>consultantList= commerciale.getConsultantList();

        return mainService.getConsultantDTO(commercialeId);

    }
    @GetMapping("/getConsByRHId")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Consultant> getConsultantByRHId(@RequestParam Long RhId){
        GestionnaireRH gestionnaireRH=gesionnaireRHRepository.findById(RhId).orElseThrow(() -> new RuntimeException("Commerciale not found"));
        return gestionnaireRH.getConsultantList();
    }
    @PostMapping("/saveOffre")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<?> addOffre(@RequestBody Offre offre){
        offreRepository.save(offre);
        return ResponseEntity.ok(new MessageResponse("Offre created successfully!"));
    }
    @GetMapping("/getOffresById")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    public List<Offre> getOffreList(@RequestParam Long gestionnaireId){
        GestionnaireRH gestionnaireRH=gesionnaireRHRepository.findById(gestionnaireId).orElseThrow(()-> new RuntimeException("Gestionnaire not found!"));
        return gestionnaireRH.getOffreList();
    }


    @GetMapping("/getOffres")
    public List<Offre> getOffres(){
        List<Offre>offreList=offreRepository.findAll();
        List<Offre>activeList=new ArrayList<Offre>();
        for(int i=0;i<offreList.size();i++){
            if(offreList.get(i).getStatus()==null){
                continue;
            }
            else if(offreList.get(i).getStatus().equals("Active")){
                activeList.add(offreList.get(i));
            }
            
        }
        return activeList;
    }
    @GetMapping("/getGesConsultants")
    public List<Consultant> getConsultant(@RequestParam Long gestId){
        GestionnaireRH gestionnaireRH=gesionnaireRHRepository.findById(gestId).orElseThrow(()-> new RuntimeException("Gestionnaire not found!"));
        return gestionnaireRH.getConsultantList();
    }
    @PostMapping("/updateConsultant")
    public Consultant updateConsultant(@RequestBody Consultant consultant){
        return mainService.updateConsultant(consultant);

    }

    @PostMapping("/saveEducation")
    public Formations saveFormation(@RequestBody Formations formations){
        System.out.println(formations.getId());
        System.out.println("***************************************************************");
        return formationsRepository.save(formations);
    }
    @GetMapping("/getConsById")
    public Consultant getConsultantById(@RequestParam Long consId){
        Consultant consultant=consultantRepository.findById(consId).orElseThrow();
        return consultant;
    }
    @PostMapping("/saveClient")
    public Client saveClient(@RequestBody Client client){
        return clientRepository.save(client);
    }
    @GetMapping("/getClients")
    public List<Client> getClients(){
        return clientRepository.findAll();
    }
    @PostMapping("/saveProjet")
    public  Projet saveProjet(@RequestBody Projet projet){
        return projetRepository.save(projet);
    }
    @GetMapping("/getAffectations")
    public List<ExperienceDTO> getAffectations(@RequestParam Long consId){
        return mainService.getExperienceDTO(consId);
    }
    @PostMapping("/saveSkill")
    public Skill saveSkill(@RequestBody Skill skill){
        return skillRepository.save(skill);
    }
    @GetMapping("/getSkills")
    public List<Skill> getSkills(@RequestParam Long consId){
        Consultant consultant=consultantRepository.findById(consId).orElseThrow();
        return consultant.getSkillList();
    }
}
