package com.bezkoder.springjwt.mappers;

import com.bezkoder.springjwt.DTO.FormationsDTO;
import com.bezkoder.springjwt.models.Formations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FormationsMapper {
    FormationsMapper INSTANCE= Mappers.getMapper(FormationsMapper.class);
    @Mapping(source = "consultant.id",target = "consultantId")
    FormationsDTO toDTO(Formations formations);
    @Mapping(source = "consultantId",target = "consultant.id")
    Formations toEntity(FormationsDTO formationsDTO);
}
