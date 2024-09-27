package com.bezkoder.springjwt.mappers;
import com.bezkoder.springjwt.DTO.SkillDTO;
import com.bezkoder.springjwt.models.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface SkillMapper {
    SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);
    @Mapping(source = "consultant.id", target = "consultantId")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    SkillDTO toDTO(Skill skill);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "consultantId", target = "consultant.id")
    Skill toEntity(SkillDTO skillDTO);

}
