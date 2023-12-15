package com.evenement1.evenement1.Controller;

import com.evenement1.evenement1.DTO.OrganisateurDTO;
import com.evenement1.evenement1.Services.OrganisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organisateurs")
@CrossOrigin(origins = "http://localhost:4200")
public class OrganisateurController {
    private final OrganisateurService organisateurService;

    @Autowired
    public OrganisateurController(OrganisateurService organisateurService) {
        this.organisateurService = organisateurService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrganisateurDTO>> getAllOrganisateurs() {
        List<OrganisateurDTO> organisateurs = organisateurService.getAllOrganisateurs();
        return new ResponseEntity<>(organisateurs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganisateurDTO> getOrganisateurById(@PathVariable Long id) {
        OrganisateurDTO organisateur = organisateurService.getOrganisateurById(id);
        if (organisateur != null) {
            return new ResponseEntity<>(organisateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<OrganisateurDTO> createOrganisateur(@RequestBody OrganisateurDTO organisateurDTO) {
        OrganisateurDTO createdOrganisateur = organisateurService.createOrganisateur(organisateurDTO);
        return new ResponseEntity<>(createdOrganisateur, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrganisateurDTO> updateOrganisateur(@PathVariable Long id, @RequestBody OrganisateurDTO organisateurDTO) {
        OrganisateurDTO updatedOrganisateur = organisateurService.updateOrganisateur(id, organisateurDTO);
        if (updatedOrganisateur != null) {
            return new ResponseEntity<>(updatedOrganisateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrganisateur(@PathVariable Long id) {
        String deletionMessage = organisateurService.deleteOrganisateur(id);

        if (deletionMessage.startsWith("Organisateur with ID") && deletionMessage.endsWith("successfully deleted.")) {
            // If the deletion message indicates success
            return new ResponseEntity<>(deletionMessage, HttpStatus.OK);
        } else {
            // If the deletion message indicates "Not Found" or other errors
            return new ResponseEntity<>(deletionMessage, HttpStatus.NOT_FOUND);
        }
    }
}
