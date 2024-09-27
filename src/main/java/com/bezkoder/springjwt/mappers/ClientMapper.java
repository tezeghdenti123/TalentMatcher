package com.bezkoder.springjwt.mappers;

import com.bezkoder.springjwt.DTO.ClientDTO;
import com.bezkoder.springjwt.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE= Mappers.getMapper(ClientMapper.class);
    ClientDTO toDTO(Client client);
    Client toEntity(ClientDTO clientDTO);
}
