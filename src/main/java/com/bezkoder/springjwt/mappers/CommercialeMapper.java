package com.bezkoder.springjwt.mappers;

import com.bezkoder.springjwt.DTO.CommercialeDTO;
import com.bezkoder.springjwt.models.Commerciale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommercialeMapper {
    CommercialeMapper INSTANCE= Mappers.getMapper(CommercialeMapper.class);
    @Mapping(source = "commerciale.id",target = "commercialeId")
    @Mapping(source = "gestionnaireRH.id",target = "gestionnaireId")
    CommercialeDTO toDTO(Commerciale commerciale);
    @Mapping(source = "commercialeId",target = "commerciale.id")
    @Mapping(source = "gestionnaireId",target = "gestionnaireRH.id")
    Commerciale toEntity(CommercialeDTO commercialeDTO);
}
