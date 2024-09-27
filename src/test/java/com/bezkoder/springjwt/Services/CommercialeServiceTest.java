package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.DTO.CommercialeDTO;
import com.bezkoder.springjwt.models.Commerciale;
import com.bezkoder.springjwt.models.Consultant;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.repositories.CommercialeRepository;
import com.bezkoder.springjwt.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")  // Use the test profile for H2
@Transactional
class CommercialeServiceTest {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CommercialeRepository commercialeRepository;
    @Autowired
    private CommercialeService commercialeService;

    @Test
    void getCommerciales() {
        //Given: a commerciale is saved in the database
        Commerciale commerciale=setCommerciale(ERole.ROLE_COMMERCIALE,"commerciale","Commerciale","commerciale@gmail.com","Test2019");
        commercialeRepository.save(commerciale);
        //when: calling the function getCommerciales
        List<CommercialeDTO>commercialeList=commercialeService.getCommerciales();
        //Then: assert that the list of commerciale of length equal 1
        assertEquals(1,commercialeList.size());
    }

    private Set<Role> setRoles(ERole erole){
        Set<Role> roles=new HashSet<>();
        Role role=new Role(erole);
        role=roleRepository.save(role);
        roles.add(role);
        return roles;

    }
    private Commerciale setCommerciale(ERole eRole, String name, String username, String email, String password){
        Commerciale commerciale= new Commerciale();
        commerciale.setName(name);
        commerciale.setEmail(email);
        commerciale.setUsername(username);
        commerciale.setPassword(password);
        Set<Role>roles1=setRoles(eRole);
        commerciale.setRoles(roles1);
        return commerciale;
    }
}