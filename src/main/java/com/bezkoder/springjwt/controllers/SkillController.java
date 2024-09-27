package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.DTO.SkillDTO;
import com.bezkoder.springjwt.Services.SkillService;
import com.bezkoder.springjwt.models.Skill;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class SkillController {
    @Autowired
    private SkillService skillService;


    @GetMapping("/consultant/skills")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMMERCIALE')")
    public List<SkillDTO> getSkills(@RequestParam Long consId){
        return skillService.getConsultantSkills(consId);
    }
    @DeleteMapping("/skill/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMMERCIALE')")
    public ResponseEntity<?> deleteSkill(@PathVariable("id") Long skillId){
        return skillService.deleteSkill(skillId);
    }



    @PostMapping("/skill")
    public ResponseEntity<MessageResponse> saveSkill(@RequestBody SkillDTO skillDTO){
        return skillService.saveSkill(skillDTO);
    }

}
