package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.DTO.CommercialeDTO;
import com.bezkoder.springjwt.mappers.CommercialeMapper;
import com.bezkoder.springjwt.models.Commerciale;
import com.bezkoder.springjwt.repositories.CommercialeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommercialeService {
    @Autowired
    private CommercialeRepository commercialeRepository;
    //Test implemented
    public List<CommercialeDTO> getCommerciales() {
        List<Commerciale>commercialeList= commercialeRepository.findAll();
        List<CommercialeDTO>commercialeDTOList=new ArrayList<CommercialeDTO>();
        for(int i=0;i<commercialeList.size();i++){
            CommercialeDTO commercialeDTO= CommercialeMapper.INSTANCE.toDTO(commercialeList.get(i));
            commercialeDTOList.add(commercialeDTO);
        }
        return commercialeDTOList;
    }
}
