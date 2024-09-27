package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.DTO.SkillDTO;
import com.bezkoder.springjwt.controllers.SkillController;
import com.bezkoder.springjwt.mappers.SkillMapper;
import com.bezkoder.springjwt.models.Consultant;
import com.bezkoder.springjwt.models.Skill;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repositories.ConsultantRepository;
import com.bezkoder.springjwt.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ConsultantRepository consultantRepository;

    public ResponseEntity<MessageResponse> saveSkill(SkillDTO skillDTO) {
        Skill skillEntity = SkillMapper.INSTANCE.toEntity(skillDTO);
        skillRepository.save(skillEntity);
        return ResponseEntity.ok(new MessageResponse("Client added successfully!"));
    }


    public List<SkillDTO> getConsultantSkills(Long consId) {
        Consultant consultant=consultantRepository.findById(consId).orElseThrow();
        List<SkillDTO>skillDTOList=new ArrayList<SkillDTO>();
        List<Skill> skillList=consultant.getSkillList();
        for(int i=0;i<skillList.size();i++){
            SkillDTO skillDTO=SkillMapper.INSTANCE.toDTO(skillList.get(i));
            skillDTOList.add(skillDTO);
        }

        return skillDTOList;
    }


    public ResponseEntity<?> deleteSkill(Long skillId) {
        if(skillRepository.existsById(skillId)){
            skillRepository.deleteById(skillId);
        }
        else{
            return ResponseEntity.ok("This Id: "+skillId+" is not present");
        }
        return ResponseEntity.ok("Deleted succefully");
    }
}
