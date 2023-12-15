package com.evenement1.evenement1.Services;

import com.evenement1.evenement1.DTO.OrganisationDTO;
import com.evenement1.evenement1.Models.Evenement;
import com.evenement1.evenement1.Models.Organisateur;
import com.evenement1.evenement1.Models.Organisation;
import com.evenement1.evenement1.Repositories.EvenementRepo;
import com.evenement1.evenement1.Repositories.OrganisateurRepo;
import com.evenement1.evenement1.Repositories.OrganisationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class OrganisationService {
    private final OrganisationRepo organisationRepo;
    private final EvenementRepo evenementRepo;
    private final OrganisateurRepo organisateurRepo;

    @Autowired
    public OrganisationService(OrganisationRepo organisationRepo, EvenementRepo evenementRepo, OrganisateurRepo organisateurRepo) {
        this.organisationRepo = organisationRepo;
        this.evenementRepo = evenementRepo;
        this.organisateurRepo = organisateurRepo;
    }

    public List<OrganisationDTO> getAllOrganisations() {
        List<Organisation> organisations = organisationRepo.findAll();
        return organisations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrganisationDTO getOrganisationById(Long id) {
        Optional<Organisation> organisationOptional = organisationRepo.findById(id);
        return organisationOptional.map(this::convertToDTO).orElse(null);
    }

    public OrganisationDTO createOrganisation(OrganisationDTO organisationDTO) {
        Organisation organisation = convertToEntity(organisationDTO);
        Organisation savedOrganisation = organisationRepo.save(organisation);
        return convertToDTO(savedOrganisation);
    }

    public OrganisationDTO updateOrganisation(Long id, OrganisationDTO organisationDTO) {
        Optional<Organisation> existingOrganisationOptional = organisationRepo.findById(id);

        if (existingOrganisationOptional.isPresent()) {
            Organisation existingOrganisation = existingOrganisationOptional.get();
            BeanUtils.copyProperties(organisationDTO, existingOrganisation, "id");
            Organisation updatedOrganisation = organisationRepo.save(existingOrganisation);
            return convertToDTO(updatedOrganisation);
        } else {
            return null; // Handle the case where the organisation with the given id is not found
        }
    }

    public String deleteOrganisation(Long id) {
        Optional<Organisation> existingOrganisationOptional = organisationRepo.findById(id);

        if (existingOrganisationOptional.isPresent()) {
            organisationRepo.deleteById(id);
            return "Organisation with ID " + id + " successfully deleted.";
        } else {
            return "Organisation with ID " + id + " not found.";
        }
    }


    private OrganisationDTO convertToDTO(Organisation organisation) {
        return OrganisationDTO.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .id_evenement(organisation.getEvenement().getId())
                .id_organisateur(organisation.getOrganisateur().getId())
                .build();
    }

    private Organisation convertToEntity(OrganisationDTO organisationDTO) {
        Organisation organisation = Organisation.builder()
                .id(organisationDTO.getId())
                .name(organisationDTO.getName())
                .build();

        // Récupérer l'Evenement à partir de la base de données
        Optional<Evenement> evenementOptional = evenementRepo.findById(organisationDTO.getId_evenement());
        evenementOptional.ifPresent(organisation::setEvenement);

        // Récupérer l'Organisateur à partir de la base de données
        Optional<Organisateur> organisateurOptional = organisateurRepo.findById(organisationDTO.getId_organisateur());
        organisateurOptional.ifPresent(organisation::setOrganisateur);

        return organisation;
    }
}
