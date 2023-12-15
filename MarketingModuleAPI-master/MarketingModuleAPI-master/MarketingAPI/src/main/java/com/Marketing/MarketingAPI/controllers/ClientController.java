package com.Marketing.MarketingAPI.controllers;
import com.Marketing.MarketingAPI.DTO.ClientDTO;
import com.Marketing.MarketingAPI.models.Gender;
import com.Marketing.MarketingAPI.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;
    @GetMapping("/all")
    public ResponseEntity<List<ClientDTO>> getAllClients(){
        List<ClientDTO> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("id") Long id){
        ClientDTO clientDTO = clientService.getClientById(id);
        return new ResponseEntity<>(clientDTO,HttpStatus.OK);
    }
    @GetMapping("/by-gender/{gender}")
    public List<ClientDTO> getClientsByGender(@PathVariable("gender") Gender gender) {
        return clientService.getClientsByGender(gender);
    }
    @GetMapping("/by-country/{country}")
    public List<ClientDTO> getClientsByCountry(@PathVariable("country")  String country) {
        return clientService.getClientsByCountry(country);
    }
    @GetMapping("/by-age-scale/{max}/{min}")
    public List<ClientDTO> getClientsByAgeScale(@PathVariable("max") int max, @PathVariable("min") int min) {
        return clientService.getClientsByAgeScale(max, min);
    }
    @PostMapping("/add")
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO){
        ClientDTO savedClient = clientService.addClient(clientDTO);
        return new ResponseEntity<>(savedClient,HttpStatus.CREATED);
    }
}
