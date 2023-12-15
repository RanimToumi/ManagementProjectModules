package com.evenement1.evenement1.Controller;

import com.evenement1.evenement1.DTO.OrganisationDTO;
import com.evenement1.evenement1.Services.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organisations")
@CrossOrigin(origins = "http://localhost:4200")
public class OrganisationController {
    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrganisationDTO>> getAllOrganisations() {
        List<OrganisationDTO> organisations = organisationService.getAllOrganisations();
        return new ResponseEntity<>(organisations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganisationDTO> getOrganisationById(@PathVariable Long id) {
        OrganisationDTO organisation = organisationService.getOrganisationById(id);
        if (organisation != null) {
            return new ResponseEntity<>(organisation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<OrganisationDTO> createOrganisation(@RequestBody OrganisationDTO organisationDTO) {
        OrganisationDTO createdOrganisation = organisationService.createOrganisation(organisationDTO);
        return new ResponseEntity<>(createdOrganisation, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrganisationDTO> updateOrganisation(@PathVariable Long id, @RequestBody OrganisationDTO organisationDTO) {
        OrganisationDTO updatedOrganisation = organisationService.updateOrganisation(id, organisationDTO);
        if (updatedOrganisation != null) {
            return new ResponseEntity<>(updatedOrganisation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrganisation(@PathVariable Long id) {
        String deletionMessage = organisationService.deleteOrganisation(id);

        if (deletionMessage.startsWith("Organisation with ID") && deletionMessage.endsWith("successfully deleted.")) {
            // If the deletion message indicates success
            return new ResponseEntity<>(deletionMessage, HttpStatus.OK);
        } else {
            // If the deletion message indicates "Not Found" or other errors
            return new ResponseEntity<>(deletionMessage, HttpStatus.NOT_FOUND);
        }
    }
}
