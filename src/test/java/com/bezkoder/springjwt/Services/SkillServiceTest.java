package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.DTO.SkillDTO;
import com.bezkoder.springjwt.mappers.SkillMapper;
import com.bezkoder.springjwt.models.Skill;
import com.bezkoder.springjwt.repositories.ConsultantRepository;
import com.bezkoder.springjwt.repositories.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SkillServiceTest {
    @InjectMocks
    private SkillService skillService;

    @Mock
    private ConsultantRepository consultantRepository;
    @Mock
    private SkillRepository skillRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveSkill() {
        //Given
        Skill skill=new Skill();
        skill.setName("Test");
        //Mock
        Mockito.when(skillRepository.save(ArgumentMatchers.any(Skill.class)))
                .thenReturn(skill);
        SkillDTO skillDTO= SkillMapper.INSTANCE.toDTO(skill);
        //when
        skillService.saveSkill(skillDTO);
        //then
        //assertEquals(skill1.getName(),"Test");
        verify(skillRepository,times(1)).save(ArgumentMatchers.any(Skill.class));
    }

    @Test
    void getConsultantSkills() {
    }
}