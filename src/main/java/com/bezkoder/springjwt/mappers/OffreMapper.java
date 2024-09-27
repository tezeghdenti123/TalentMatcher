package com.bezkoder.springjwt.mappers;

import com.bezkoder.springjwt.DTO.OffreDTO;
import com.bezkoder.springjwt.models.Offre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OffreMapper {
    OffreMapper INSTANCE= Mappers.getMapper(OffreMapper.class);
    @Mapping(source = "id",target = "id")
    @Mapping(source = "title",target = "title")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "tjm",target = "tjm")
    @Mapping(source = "dateDebut",target = "dateDebut")
    @Mapping(source = "location",target = "location")
    @Mapping(source = "type",target = "type")
    @Mapping(source = "status",target = "status")
    @Mapping(source = "position",target = "position")
    @Mapping(source = "experience",target = "experience")
    @Mapping(source = "gestionnaireRH.id",target = "gestionnaireId")
    OffreDTO toDTO(Offre offre);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "title",target = "title")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "tjm",target = "tjm")
    @Mapping(source = "dateDebut",target = "dateDebut")
    @Mapping(source = "location",target = "location")
    @Mapping(source = "type",target = "type")
    @Mapping(source = "status",target = "status")
    @Mapping(source = "position",target = "position")
    @Mapping(source = "experience",target = "experience")
    @Mapping(source = "gestionnaireId",target = "gestionnaireRH.id")
    Offre toEntity(OffreDTO offreDTO);
}
