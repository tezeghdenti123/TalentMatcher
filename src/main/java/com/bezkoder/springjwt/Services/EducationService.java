package com.bezkoder.springjwt.Services;
import com.bezkoder.springjwt.DTO.FormationsDTO;
import com.bezkoder.springjwt.mappers.FormationsMapper;
import com.bezkoder.springjwt.models.Formations;
import com.bezkoder.springjwt.repositories.FormationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EducationService {
    @Autowired
    private FormationsRepository formationsRepository;

    public void saveFormations(FormationsDTO formationsDTO) {
        Formations formations= FormationsMapper.INSTANCE.toEntity(formationsDTO);
        formationsRepository.save(formations);
    }

    public ResponseEntity<?> deleteFormation(Long formationId) {
        if(formationsRepository.existsById(formationId)){
            formationsRepository.deleteById(formationId);
        }
        else{
            return ResponseEntity.ok("This Id: "+formationId+" doesn't exist");
        }
        return ResponseEntity.ok("Education deleted succecfully!");
    }
}
