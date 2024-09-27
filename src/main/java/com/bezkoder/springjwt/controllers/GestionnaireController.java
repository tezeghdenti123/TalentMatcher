package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.DTO.GestionnaireRhDTO;
import com.bezkoder.springjwt.Services.GestionnaireService;
import com.bezkoder.springjwt.models.GestionnaireRH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class GestionnaireController {
    @Autowired
    private GestionnaireService gestionnaireService;
    @GetMapping("/gestionnaires")
    public List<GestionnaireRhDTO> getGestionnaire(){
        return gestionnaireService.getGestionnaires();
    }
}
