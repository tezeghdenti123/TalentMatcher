package com.bezkoder.springjwt.repositories;

import com.bezkoder.springjwt.models.Commerciale;
import com.bezkoder.springjwt.models.Consultant;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class CommercialeRepositoryTest {
    @Autowired
    private CommercialeRepository commercialeRepository;
    @Autowired
    private ConsultantRepository consultantRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Test
    public void saveCommerciale(){
        Consultant consultant=new Consultant();
        consultant.setName("Consultant1");
        consultant.setEmail("consultant@gmail.com");
        consultant.setUsername("consultant");
        Set<Role>roles=new HashSet<>();
        Role role=new Role(ERole.ROLE_DEVELOPPER);
        role=roleRepository.save(role);
        roles.add(role);
        consultant.setRoles(roles);
        consultant.setPassword("test2019");
        Commerciale commerciale= new Commerciale();
        commerciale.setName("Commerciale");
        commerciale.setEmail("commerciale@gmail.com");
        commerciale.setUsername("commerciale");
        commerciale.setPassword("test2019");
        Set<Role>roles1=new HashSet<>();
        Role role1=new Role();
        role1.setName(ERole.ROLE_DEVELOPPER);
        role1=roleRepository.save(role1);
        roles1.add(role1);
        commerciale.setRoles(roles1);
        List<Consultant> consultantList=new ArrayList<Consultant>();
        consultantList.add(consultant);
        commerciale.setConsultantList(consultantList);
        commercialeRepository.save(commerciale);
        System.out.println(consultantRepository.findAll().get(0).getName());
        System.out.println(consultantRepository.findAll().get(1).getName());
        assertEquals(2,consultantRepository.findAll().size());
    }
    @Test
    public void deleteCommerciale(){
        Consultant consultant=new Consultant();
        consultant.setName("Consultant1");
        consultant.setEmail("consultant@gmail.com");
        consultant.setUsername("consultant");
        Set<Role>roles=new HashSet<>();
        Role role=new Role(ERole.ROLE_DEVELOPPER);
        role=roleRepository.save(role);
        roles.add(role);
        consultant.setRoles(roles);
        consultant.setPassword("test2019");
        Commerciale commerciale= new Commerciale();
        commerciale.setName("Commerciale");
        commerciale.setEmail("commerciale@gmail.com");
        commerciale.setUsername("commerciale");
        commerciale.setPassword("test2019");
        Set<Role>roles1=new HashSet<>();
        Role role1=new Role();
        role1.setName(ERole.ROLE_DEVELOPPER);
        role1=roleRepository.save(role1);
        roles1.add(role1);
        commerciale.setRoles(roles1);
        List<Consultant> consultantList=new ArrayList<Consultant>();
        consultantList.add(consultant);
        commerciale.setConsultantList(consultantList);
        commerciale=commercialeRepository.save(commerciale);
        assertEquals(2,consultantRepository.findAll().size());
        commercialeRepository.delete(commerciale);
        assertEquals(1,consultantRepository.findAll().size());
    }
}