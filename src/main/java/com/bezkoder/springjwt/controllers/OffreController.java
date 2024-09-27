package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.DTO.OffreDTO;
import com.bezkoder.springjwt.Services.OffreService;
import com.bezkoder.springjwt.models.Offre;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class OffreController {
    @Autowired
    private OffreService offreService;

    @GetMapping("/offres")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OffreDTO> getOffres(){
        return offreService.getOffres();
    }
    @GetMapping("/gestionnaire/offres")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public List<OffreDTO> getOffreList(@RequestParam Long gestionnaireId){
        return offreService.getOffreByGestionnaireId(gestionnaireId);
    }


    @GetMapping("/active-offres")
    public List<OffreDTO> getActiveOffres(){
        return offreService.getActiveOffres();
    }


    @PostMapping("/offre")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<?> addOffre(@RequestBody OffreDTO offreDTO){


        return offreService.saveOffre(offreDTO);
    }
    @PutMapping("/offre")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<?> updateOffre(@RequestBody OffreDTO offreDTO){
        return offreService.updateOffre(offreDTO);
    }
    @DeleteMapping("/offre/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<?>deleteOffre(@PathVariable("id")Long offreId){
        return offreService.deleteOffre(offreId);
    }


}
