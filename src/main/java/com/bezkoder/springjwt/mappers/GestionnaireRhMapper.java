package com.bezkoder.springjwt.mappers;

import com.bezkoder.springjwt.DTO.CommercialeDTO;
import com.bezkoder.springjwt.DTO.GestionnaireRhDTO;
import com.bezkoder.springjwt.models.Commerciale;
import com.bezkoder.springjwt.models.GestionnaireRH;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface GestionnaireRhMapper {
    GestionnaireRhMapper INSTANCE= Mappers.getMapper(GestionnaireRhMapper.class);
    @Mapping(source = "commerciale.id",target = "commercialeId")
    @Mapping(source = "gestionnaireRH.id",target = "gestionnaireId")
    GestionnaireRhDTO toDTO(GestionnaireRH gestionnaireRH);
    @Mapping(source = "commercialeId",target = "commerciale.id")
    @Mapping(source = "gestionnaireId",target = "gestionnaireRH.id")
    GestionnaireRH toEntity(GestionnaireRhDTO gestionnaireRhDTO);
}
