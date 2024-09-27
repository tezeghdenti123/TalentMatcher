package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.DTO.ConsultantPrivateDTO;
import com.bezkoder.springjwt.DTO.ConsultantPublicDTO;
import com.bezkoder.springjwt.mappers.ConsultantMapper;
import com.bezkoder.springjwt.mappers.ConsultantPrivateMapper;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repositories.CommercialeRepository;
import com.bezkoder.springjwt.repositories.ConsultantRepository;
import com.bezkoder.springjwt.repositories.GesionnaireRHRepository;
import com.bezkoder.springjwt.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class ConsultantService {
    @Autowired
    private GesionnaireRHRepository gesionnaireRHRepository;
    @Autowired
    private ConsultantRepository consultantRepository;
    @Autowired
    private CommercialeRepository commercialeRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;



    public List<ConsultantPublicDTO> getConsultantByGestionnaireId(Long gestId) {
        GestionnaireRH gestionnaireRH=gesionnaireRHRepository.findById(gestId).orElseThrow(()-> new EntityNotFoundException("Gestionnaire not found!"));
        List<Consultant>consultantList= gestionnaireRH.getConsultantList();
        List<ConsultantPublicDTO> consultantPublicDTOList =new ArrayList<ConsultantPublicDTO>();
        for(int i=0;i<consultantList.size();i++){
            ConsultantPublicDTO consultantPublicDTO = ConsultantMapper.INSTANCE.toDTO(consultantList.get(i));
            consultantPublicDTOList.add(consultantPublicDTO);
        }
        return consultantPublicDTOList;
    }

    public List<ConsultantPublicDTO> getConsultants() {
        List<Consultant>consultantList=consultantRepository.findAll();
        List<ConsultantPublicDTO> consultantPublicDTOList =new ArrayList<ConsultantPublicDTO>();
        for(int i=0;i<consultantList.size();i++){
            ConsultantPublicDTO consultantPublicDTO = ConsultantMapper.INSTANCE.toDTO(consultantList.get(i));
            consultantPublicDTOList.add(consultantPublicDTO);
        }
        return consultantPublicDTOList;
    }

    public List<ConsultantPublicDTO> getConsultantDTOs(){
        List<Consultant>consultantList= consultantRepository.findAll();
        List<ConsultantPublicDTO> consultantPublicDTOList =new ArrayList<ConsultantPublicDTO>();
        for(int i=0;i<consultantList.size();i++ ){
            Consultant consultant=consultantList.get(i);
            ConsultantPublicDTO consultantPublicDTO = ConsultantMapper.INSTANCE.toDTO(consultant);
            List<Affectation>affectationList=consultant.getAffectationList();
            Boolean available=isAvailable(affectationList);
            consultantPublicDTO.setAvailable(available);
            consultantPublicDTOList.add(consultantPublicDTO);
        }
        return consultantPublicDTOList;
    }

    public List<ConsultantPublicDTO> getConsultantDTOByConsId(Long commercialeId){
        Commerciale commerciale=commercialeRepository.findById(commercialeId).orElseThrow(() -> new RuntimeException("Commerciale not found"));
        List<Consultant>consultantList= commerciale.getConsultantList();
        List<ConsultantPublicDTO> consultantPublicDTOList =new ArrayList<ConsultantPublicDTO>();
        for(int i=0;i<consultantList.size();i++ ){
            Consultant consultant=consultantList.get(i);
            ConsultantPublicDTO consultantPublicDTO = ConsultantMapper.INSTANCE.toDTO(consultant);
            List<Affectation>affectationList=consultant.getAffectationList();
            Boolean available=isAvailable(affectationList);
            consultantPublicDTO.setAvailable(available);
            consultantPublicDTOList.add(consultantPublicDTO);
        }
        return consultantPublicDTOList;
    }

    //Test implemented
    public Boolean isAvailable(List<Affectation>affectationList){
        for(int i=0;i<affectationList.size();i++){
            Affectation affectation=affectationList.get(i);
            if(isCurrent(affectation.getDate_deb(),affectation.getDate_fin())){
                return false;
            }
        }

        return true;
    }


    //Test implemented
    public Boolean isCurrent(LocalDate dateDebut, LocalDate dateFin){
        LocalDate currentDate=LocalDate.now();
        if(dateDebut!=null&&dateFin!=null){
            return (currentDate.isAfter(dateDebut)&&currentDate.isBefore(dateFin))||(currentDate.isEqual(dateDebut)&&currentDate.isBefore(dateFin));
        }
        return false;
    }


    //Test implemented
    public ConsultantPublicDTO updateConsultant(ConsultantPublicDTO consultantPublicDTO){
        Optional<Consultant> savedConsultantOptional=consultantRepository.findById(consultantPublicDTO.getId());
        if(savedConsultantOptional.isPresent()){

            Consultant savedConsultant=savedConsultantOptional.get();
            savedConsultant.setLinkedIn(consultantPublicDTO.getLinkedIn());
            savedConsultant.setName(consultantPublicDTO.getName());
            savedConsultant.setPhone(consultantPublicDTO.getPhone());
            savedConsultant.setUsername(consultantPublicDTO.getUsername());
            savedConsultant.setEmail(consultantPublicDTO.getEmail());
            savedConsultant.setTitle(consultantPublicDTO.getTitle());

            Consultant consultant=consultantRepository.save(savedConsultant);
            return ConsultantMapper.INSTANCE.toDTO(consultant);
        }
        return null;

    }



    public ConsultantPublicDTO getConsultantById(Long consId) {
        Consultant consultant= consultantRepository.findById(consId).orElseThrow();
        return ConsultantMapper.INSTANCE.toDTO(consultant);
    }


    //Test implemented
    public ResponseEntity<?> updateFullConsultant(ConsultantPrivateDTO consultantPrivateDTO) {
        Consultant consultant= ConsultantPrivateMapper.INSTANCE.toEntity(consultantPrivateDTO);
        if(consultantRepository.existsById(consultant.getId())){
            Consultant existedConsultant=consultantRepository.findById(consultant.getId()).orElseThrow();
            existedConsultant.setName(consultant.getName());
            existedConsultant.setUsername(consultant.getUsername());
            existedConsultant.setEmail(consultant.getEmail());
            existedConsultant.setPhone(consultant.getPhone());
            existedConsultant.setTitle(consultant.getTitle());
            existedConsultant=updateConsultantRoles(existedConsultant,consultantPrivateDTO);
            existedConsultant=updateConsultantCommerciale(existedConsultant,consultantPrivateDTO);
            existedConsultant=updateConsultantGestionnaire(existedConsultant,consultantPrivateDTO);
            existedConsultant.setPassword(encoder.encode(consultant.getPassword()));
            consultantRepository.save(existedConsultant);
            return ResponseEntity.status(HttpStatus.OK).body("Updated!");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("Id not found!");
        }
    }

    //Test implemented
    Consultant updateConsultantGestionnaire(Consultant savedConsultant,ConsultantPrivateDTO consultantPrivateDTO){
        if(consultantPrivateDTO.getGestionnaireId()!=null){
            Consultant consultant=ConsultantPrivateMapper.INSTANCE.toEntity(consultantPrivateDTO);
            System.out.println(consultant.getGestionnaireRH().getId());
            savedConsultant.setGestionnaireRH(consultant.getGestionnaireRH());
        }
        else{
            savedConsultant.setGestionnaireRH(null);
        }
        return savedConsultant;
    }
    //Test implemented
    Consultant updateConsultantCommerciale(Consultant savedConsultant,ConsultantPrivateDTO consultantPrivateDTO){
        if(consultantPrivateDTO.getCommercialeId()!=null){
            Consultant consultant=ConsultantPrivateMapper.INSTANCE.toEntity(consultantPrivateDTO);
            savedConsultant.setCommerciale(consultant.getCommerciale());
        }
        else{
            savedConsultant.setCommerciale(null);
        }
        return savedConsultant;
    }
    //Test implemented
    Consultant updateConsultantRoles(Consultant savedConsultant,ConsultantPrivateDTO consultantPrivateDTO){
        if(consultantPrivateDTO.getRoles()!=null){
            Iterator<Role> iterator = consultantPrivateDTO.getRoles().iterator();
            Role role=iterator.next();
            Role role1=roleRepository.findByName(role.getName()).orElseThrow();
            Set<Role> newRoles=new HashSet<>();
            newRoles.add(role1);
            savedConsultant.setRoles(newRoles);
            return savedConsultant;
        }
        else{
            savedConsultant.setRoles(null);
            return savedConsultant;
        }
    }

    //Test implemented
    @Transactional
    public ResponseEntity<?> deleteConsultant(Long consId) {
        if(consultantRepository.existsById(consId)){
            Consultant consultant=consultantRepository.findById(consId).orElseThrow();
            System.out.println("ok");
            System.out.println(consultant.getRoles().iterator().next().getName());
            System.out.println("ok");
            String role= String.valueOf(consultant.getRoles().iterator().next().getName());
            if(role.equals("ROLE_DEVELOPPER")){
                System.out.println("Dev");
                consultantRepository.deleteById(consId);
            }
            else if (role.equals("ROLE_GESTIONNAIRE")) {
                System.out.println(consId);
                System.out.println("Gest");
                GestionnaireRH gestionnaireRH=gesionnaireRHRepository.findById(consId).orElseThrow();
                System.out.println("ok");
                gestionnaireRH.getConsultantList().forEach(consultant1 -> {consultant1.setGestionnaireRH(null);});
                gesionnaireRHRepository.deleteById(consId);
            }
            else {
                System.out.println("Com");
                Commerciale commerciale=commercialeRepository.findById(consId).orElseThrow();
                commerciale.getConsultantList().forEach(consultant1 -> {consultant1.setCommerciale(null);});
                commercialeRepository.deleteById(consId);
            }

        }

        return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
    }
}
