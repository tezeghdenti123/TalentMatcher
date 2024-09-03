package com.bezkoder.springjwt.Main;
import com.bezkoder.springjwt.DTO.ConsultantDTO;
import com.bezkoder.springjwt.DTO.ExperienceDTO;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MainService {
    @Autowired
    private ConsultantRepository consultantRepository;
    @Autowired
    private CommercialeRepository commercialeRepository;
    @Autowired
    private GesionnaireRHRepository gesionnaireRHRepository;
    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private ClientRepository clientRepository;


    public List<ConsultantDTO> getConsultantDTO(Long commercialeId){
        Commerciale commerciale=commercialeRepository.findById(commercialeId).orElseThrow(() -> new RuntimeException("Commerciale not found"));
        List<Consultant>consultantList= commerciale.getConsultantList();
        List<ConsultantDTO>consultantDTOList=new ArrayList<ConsultantDTO>();
        for(int i=0;i<consultantList.size();i++ ){
            Consultant consultant=consultantList.get(i);
            ConsultantDTO consultantDTO=consultantToConsultantDTO(consultant);
            List<Affectation>affectationList=consultant.getAffectationList();
            Boolean available=isAvailable(affectationList);
            consultantDTO.setAvailable(available);
            consultantDTOList.add(consultantDTO);
        }
        return consultantDTOList;
    }
    public Boolean isAvailable(List<Affectation>affectationList){
        for(int i=0;i<affectationList.size();i++){
            Affectation affectation=affectationList.get(i);
            if(isCurrent(affectation.getDate_deb(),affectation.getDate_fin())){
                return false;
            }
        }
        return true;
    }
    public Boolean isCurrent(LocalDate dateDebut,LocalDate dateFin){
        LocalDate currentDate=LocalDate.now();
        if(dateDebut!=null&&dateFin!=null){
            return currentDate.isAfter(dateDebut)&&currentDate.isBefore(dateFin);
        }
        return false;
    }
    public ConsultantDTO consultantToConsultantDTO(Consultant consultant){
        ConsultantDTO consultantDTO=new ConsultantDTO();
        consultantDTO.setId(consultant.getId());
        consultantDTO.setName(consultant.getName());
        consultantDTO.setEmail(consultant.getEmail());
        consultantDTO.setUsername(consultant.getUsername());
        consultantDTO.setTitle(consultant.getTitle());
        consultantDTO.setPhone(consultant.getPhone());
        consultantDTO.setLinkedIn(consultant.getLinkedIn());
        consultantDTO.setSkillList(consultant.getSkillList());
        consultantDTO.setLanguesList(consultant.getLanguesList());
        consultantDTO.setFormationsList(consultant.getFormationsList());
        consultantDTO.setExperienceList(consultant.getExperienceList());
        consultantDTO.setCertificatsList(consultant.getCertificatsList());
        consultantDTO.setGestionnaireRH(consultant.getGestionnaireRH());
        consultantDTO.setCommerciale(consultant.getCommerciale());
        consultantDTO.setRoles(consultant.getRoles());
        consultantDTO.setPassword(consultant.getPassword());
        consultantDTO.setAffectationList(consultant.getAffectationList());
        return consultantDTO;
    }
    public Consultant updateConsultant(Consultant consultant){
        Optional<Consultant> savedConsultantOptional=consultantRepository.findById(consultant.getId());
        if(savedConsultantOptional.isPresent()){
            Consultant savedConsultant=savedConsultantOptional.get();
            savedConsultant.setLinkedIn(consultant.getLinkedIn());
            savedConsultant.setName(consultant.getName());
            savedConsultant.setPhone(consultant.getPhone());
            savedConsultant.setUsername(consultant.getUsername());
            savedConsultant.setEmail(consultant.getEmail());
            savedConsultant.setTitle(consultant.getTitle());
            return savedConsultant;
        }
        return null;

    }
    public List<ExperienceDTO>getExperienceDTO(Long consId){
        Consultant consultant=consultantRepository.findById(consId).orElseThrow();
        List<Affectation> affectationList=consultant.getAffectationList();
        List<ExperienceDTO>experienceDTOList=new ArrayList<ExperienceDTO>();
        for (int i=0;i<affectationList.size();i++){
            Affectation affectation=affectationList.get(i);
            Projet projet=projetRepository.findById(affectation.getProjet().getId()).orElseThrow();
            Client client=clientRepository.findById(projet.getClient().getId()).orElseThrow();
            ExperienceDTO experienceDTO=setExperienceDTO(affectation,projet,client);
            experienceDTOList.add(experienceDTO);
        }

        return experienceDTOList;
    }
    public ExperienceDTO setExperienceDTO(Affectation affectation,Projet projet,Client client){
        ExperienceDTO experienceDTO=new ExperienceDTO();
        experienceDTO.setAffectationId(affectation.getId());
        experienceDTO.setTjm(affectation.getTjm());
        experienceDTO.setTitre(projet.getTitre());
        experienceDTO.setDate_debut(projet.getDate_debut());
        experienceDTO.setDate_fin(projet.getDate_fin());
        experienceDTO.setClientName(client.getName());
        experienceDTO.setClientId(client.getId());
        experienceDTO.setProjectId(projet.getId());
        return  experienceDTO;
    }

}
