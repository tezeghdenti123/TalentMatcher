package com.bezkoder.springjwt.mappers;

import com.bezkoder.springjwt.DTO.ConsultantPrivateDTO;
import com.bezkoder.springjwt.DTO.ConsultantPublicDTO;
import com.bezkoder.springjwt.models.Consultant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ConsultantPrivateMapper {
    ConsultantPrivateMapper INSTANCE=Mappers.getMapper(ConsultantPrivateMapper.class);
    @Mapping(source = "commerciale.id",target = "commercialeId")
    @Mapping(source = "gestionnaireRH.id",target = "gestionnaireId")
    ConsultantPrivateDTO toDTO(Consultant consultant);
    @Mapping(source = "commercialeId",target = "commerciale.id")
    @Mapping(source = "gestionnaireId",target = "gestionnaireRH.id")
    Consultant toEntity(ConsultantPrivateDTO consultantPrivateDTO);
}
