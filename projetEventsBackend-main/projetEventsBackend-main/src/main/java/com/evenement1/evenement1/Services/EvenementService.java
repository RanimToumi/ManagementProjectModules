package com.evenement1.evenement1.Services;

import com.evenement1.evenement1.DTO.EvenementDTO;
import com.evenement1.evenement1.Models.Evenement;
import com.evenement1.evenement1.Repositories.EvenementRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class EvenementService {
    private final EvenementRepo evenementRepo;
    @Autowired
    public EvenementService(EvenementRepo evenementRepo) {
        this.evenementRepo = evenementRepo;
    }

    public List<EvenementDTO> getAllEvenements() {
        List<Evenement> evenements = evenementRepo.findAll();
        return evenements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public EvenementDTO getEvenementById(Long id) {
        Optional<Evenement> evenementOptional = evenementRepo.findById(id);
        return evenementOptional.map(this::convertToDTO).orElse(null);
    }

    public EvenementDTO createEvenement(EvenementDTO evenementDTO) {
        Evenement evenement = convertToEntity(evenementDTO);
        Evenement savedEvenement = evenementRepo.save(evenement);
        return convertToDTO(savedEvenement);
    }

    public EvenementDTO updateEvenement(Long id, EvenementDTO evenementDTO) {
        Optional<Evenement> existingEvenementOptional = evenementRepo.findById(id);

        if (existingEvenementOptional.isPresent()) {
            Evenement existingEvenement = existingEvenementOptional.get();
            BeanUtils.copyProperties(evenementDTO, existingEvenement, "id");
            Evenement updatedEvenement = evenementRepo.save(existingEvenement);
            return convertToDTO(updatedEvenement);
        } else {
            return null; // Handle the case where the evenement with the given id is not found
        }
    }

   public String deleteEvenement(Long id) {
       Optional<Evenement> existingEvenementOptional = evenementRepo.findById(id);
       if (existingEvenementOptional.isPresent()) {
           evenementRepo.deleteById(id);
           return "Evenement with ID " + id + " successfully deleted.";
       } else {
           return "Evenement with ID " + id + " not found.";
       }
   }



    private EvenementDTO convertToDTO(Evenement evenement) {
        return EvenementDTO.builder()
                .id(evenement.getId())
                .name(evenement.getName())
                .lieu(evenement.getLieu())
                .description(evenement.getDescription())
                .build();
    }

    private Evenement convertToEntity(EvenementDTO evenementDTO) {
        return Evenement.builder()
                .id(evenementDTO.getId())
                .name(evenementDTO.getName())
                .lieu(evenementDTO.getLieu())
                .description(evenementDTO.getDescription())
                .build();
    }
}
