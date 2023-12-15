package com.Marketing.MarketingAPI.services;

import com.Marketing.MarketingAPI.DTO.ClientDTO;
import com.Marketing.MarketingAPI.models.Client;
import com.Marketing.MarketingAPI.models.FieldOfActivity;
import com.Marketing.MarketingAPI.models.Gender;
import com.Marketing.MarketingAPI.repositories.ClientRepo;
import com.Marketing.MarketingAPI.repositories.FieldOfActivityRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;
    private final ModelMapper modelMapper;
    private final FieldOfActivityRepo fieldOfActivityRepo;
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepo.findAll();
        return clients.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public ClientDTO getClientById(Long id) {
        Client client = clientRepo.findById(id).get();
        return convertToDto(client);
    }
    public List<ClientDTO> getClientsByGender(Gender gender) {
        List<Client> clients = clientRepo.findByGender(gender);
        return clients.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<ClientDTO> getClientsByField(FieldOfActivity field) {
        List<Client> clients = clientRepo.findByField(field);
        return clients.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<ClientDTO> getClientsByCountry(String country) {
        List<Client> clients = clientRepo.findByCountry(country);
        return clients.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<ClientDTO> getClientsByAgeScale(int max, int min) {
        List<Client> scaledClients=new ArrayList<Client>();
        for (Client client :clientRepo.findAll()){
            if(client.getAge()<=max & client.getAge()>=min){
                scaledClients.add(client);
            }
        }
        return scaledClients.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public ClientDTO addClient(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);
        FieldOfActivity field = fieldOfActivityRepo.findById(clientDTO.getField_id())
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + clientDTO.getField_id()));
        client.setField(field);
        Client savedClient = clientRepo.save(client);
        return convertToDto(savedClient);
    }
    public ClientDTO UpdateClient(ClientDTO clientDTO){
        Client client= modelMapper.map(clientDTO,Client.class);
        Client updatedClient = clientRepo.save(client);
        return convertToDto(updatedClient);
    }
    public void DeleteClientById(long id) {
        if (id == 0) {
            log.error("clientId is null");
        }
        clientRepo.deleteById(id);
    }
    private ClientDTO convertToDto(Client client) {
        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
        if (client.getField() != null) {
            clientDTO.setField_id(client.getField().getId());
        }
        return clientDTO;
    }

}
