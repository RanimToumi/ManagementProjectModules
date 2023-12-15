package com.evenement1.evenement1.Services;

import com.evenement1.evenement1.DTO.OrganisateurDTO;
import com.evenement1.evenement1.Models.Organisateur;
import com.evenement1.evenement1.Repositories.OrganisateurRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class OrganisateurService {
    private final OrganisateurRepo organisateurRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public OrganisateurService(OrganisateurRepo organisateurRepo) {
        this.organisateurRepo = organisateurRepo;
    }

    public List<OrganisateurDTO> getAllOrganisateurs() {
        List<Organisateur> organisateurs = organisateurRepo.findAll();
        return organisateurs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrganisateurDTO getOrganisateurById(Long id) {
        Optional<Organisateur> organisateurOptional = organisateurRepo.findById(id);
        return organisateurOptional.map(this::convertToDTO).orElse(null);
    }

    public OrganisateurDTO createOrganisateur(OrganisateurDTO organisateurDTO) {
        Organisateur organisateur = convertToEntity(organisateurDTO);
        organisateur.setPassword(passwordEncoder.encode(organisateur.getPassword()));
        Organisateur savedOrganisateur = organisateurRepo.save(organisateur);
        return convertToDTO(savedOrganisateur);
    }

    public OrganisateurDTO updateOrganisateur(Long id, OrganisateurDTO organisateurDTO) {
        Optional<Organisateur> existingOrganisateurOptional = organisateurRepo.findById(id);

        if (existingOrganisateurOptional.isPresent()) {
            Organisateur existingOrganisateur = existingOrganisateurOptional.get();
            BeanUtils.copyProperties(organisateurDTO, existingOrganisateur, "id");
            Organisateur updatedOrganisateur = organisateurRepo.save(existingOrganisateur);
            return convertToDTO(updatedOrganisateur);
        } else {
            return null; // Handle the case where the organisateur with the given id is not found
        }
    }

    public String deleteOrganisateur(Long id) {
        Optional<Organisateur> existingOrganisateurOptional = organisateurRepo.findById(id);
        if (existingOrganisateurOptional.isPresent()) {
            organisateurRepo.deleteById(id);
            String successMessage = "Organisateur with ID " + id + " successfully deleted.";
            System.out.println(successMessage); // Print to console
            return successMessage;
        } else {
            return "Organisateur with ID " + id + " not found."; // Return "Not Found" message
        }
    }

    private OrganisateurDTO convertToDTO(Organisateur organisateur) {
        return OrganisateurDTO.builder()
                .id(organisateur.getId())
                .firstname(organisateur.getFirstname())
                .lastname(organisateur.getLastname())
                .email(organisateur.getEmail())
                .adresse(organisateur.getAdresse())
                .cin(organisateur.getCin())
                .phoneNumber(organisateur.getPhoneNumber())
                .password(organisateur.getPassword())
                .role(organisateur.getRole())
                .build();
    }

    private Organisateur convertToEntity(OrganisateurDTO organisateurDTO) {
        return Organisateur.builder()
                .id(organisateurDTO.getId())
                .firstname(organisateurDTO.getFirstname())
                .lastname(organisateurDTO.getLastname())
                .email(organisateurDTO.getEmail())
                .adresse(organisateurDTO.getAdresse())
                .cin(organisateurDTO.getCin())
                .phoneNumber(organisateurDTO.getPhoneNumber())
                .password(organisateurDTO.getPassword())
                .role(organisateurDTO.getRole())
                .build();
    }
}
