package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.DTO.CommercialeDTO;
import com.bezkoder.springjwt.Services.CommercialeService;
import com.bezkoder.springjwt.models.Commerciale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class CommercialeController {
    @Autowired
    private CommercialeService commercialeService;

    @GetMapping("/commerciales")
    public List<CommercialeDTO> getListCommerciale() {
        return commercialeService.getCommerciales();
    }



}
