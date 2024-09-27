package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.DTO.ClientDTO;
import com.bezkoder.springjwt.models.Client;
import com.bezkoder.springjwt.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")  // Use the test profile for H2
@Transactional
class ClientServiceTest {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;

    @Test
    void getClients() {
        //Given:no client in the data base

        //When:invoke getClients
        int nbClients=clientService.getClients().size();
        //Then:the numbers of Clients should be 0
        assertEquals(0,nbClients);

        //Given:One client in the data base
        Client client=new Client();
        client.setEmail("Client@gmail.com");
        client.setName("Client");
        client.setAdresse("client");
        client.setContact("client");
        client.setTelephone("1111");
        clientRepository.save(client);
        //When:invoke getClients
        nbClients=clientService.getClients().size();
        //Then:the numbers of Clients should be 1
        assertEquals(1,nbClients);

    }

    @Test
    void saveClient() {
        //Given: a ClientDTO
        ClientDTO clientDTO=new ClientDTO();
        clientDTO.setEmail("Client@gmail.com");
        clientDTO.setName("Client");
        clientDTO.setAdresse("client");
        clientDTO.setContact("client");
        clientDTO.setTelephone("1111");
        //When:invoke saveClients
        clientService.saveClient(clientDTO);
        int nbClients=clientRepository.findAll().size();
        //Then:the numbers of Clients should be 1
        assertEquals(1,nbClients);
    }

    @Test
    void updateClient() {
        //Given:a client already saved in data base
        Client client=new Client();
        client.setEmail("Client@gmail.com");
        client.setName("Client");
        client.setAdresse("client");
        client.setContact("client");
        client.setTelephone("1111");
        client=clientRepository.save(client);
        // When: invoke updateClient with clientdto
        ClientDTO clientDTO=new ClientDTO();
        clientDTO.setEmail("Client1@gmail.com");
        clientDTO.setName("Client1");
        clientDTO.setAdresse("Client1");
        clientDTO.setContact("Client1");
        clientDTO.setTelephone("2222");
        clientDTO.setId(client.getId());
        clientService.updateClient(clientDTO);
        client=clientRepository.findById(client.getId()).orElseThrow();
        //Then:assert that the client is up to date
        assertEquals("Client1@gmail.com",client.getEmail());
        assertEquals("Client1",client.getName());
        assertEquals("Client1",client.getAdresse());
        assertEquals("Client1",client.getContact());
    }

    @Test
    void deleteClient() {
        //Given:a client already saved in data base
        Client client=new Client();
        client.setEmail("Client@gmail.com");
        client.setName("Client");
        client.setAdresse("client");
        client.setContact("client");
        client.setTelephone("1111");
        client=clientRepository.save(client);
        assertEquals(1,clientRepository.findAll().size());
        // When: invoke deleteClient
        clientService.deleteClient(client.getId());
        //Then: the client should be deleted
        assertEquals(0,clientRepository.findAll().size());


    }
}