package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.DTO.GestionnaireRhDTO;
import com.bezkoder.springjwt.mappers.GestionnaireRhMapper;
import com.bezkoder.springjwt.models.GestionnaireRH;
import com.bezkoder.springjwt.repositories.GesionnaireRHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GestionnaireService {
    @Autowired
    private GesionnaireRHRepository gesionnaireRHRepository;
    public List<GestionnaireRhDTO> getGestionnaires() {
        List<GestionnaireRH>gestionnaireRHList= gesionnaireRHRepository.findAll();
        List<GestionnaireRhDTO>gestionnaireRhDTOList=new ArrayList<GestionnaireRhDTO>();
        for(int i=0;i<gestionnaireRHList.size();i++){
            GestionnaireRhDTO gestionnaireRhDTO= GestionnaireRhMapper.INSTANCE.toDTO(gestionnaireRHList.get(i));
            gestionnaireRhDTOList.add(gestionnaireRhDTO);
        }
        return gestionnaireRhDTOList;
    }
}
