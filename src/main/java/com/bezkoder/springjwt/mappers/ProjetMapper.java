package com.bezkoder.springjwt.mappers;

import com.bezkoder.springjwt.DTO.ProjetDTO;
import com.bezkoder.springjwt.models.Projet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjetMapper {
    ProjetMapper INSTANCE= Mappers.getMapper(ProjetMapper.class);
    @Mapping(source = "client.id",target = "clientId")
    @Mapping(source = "affectationList", target = "affectationList")
    ProjetDTO toDTO(Projet projet);
    @Mapping(source = "clientId",target = "client.id")
    @Mapping(source = "affectationList", target = "affectationList")
    Projet toEntity(ProjetDTO projetDTO);
}
