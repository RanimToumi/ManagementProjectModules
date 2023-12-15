package com.evenement1.evenement1.Controller;

import com.evenement1.evenement1.DTO.EvenementDTO;
import com.evenement1.evenement1.Services.EvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evenements")
@CrossOrigin(origins = "http://localhost:4200")
public class EvenementController {
    private final EvenementService evenementService;

    @Autowired
    public EvenementController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EvenementDTO>> getAllEvenements() {
        List<EvenementDTO> evenements = evenementService.getAllEvenements();
        return new ResponseEntity<>(evenements, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvenementDTO> getEvenementById(@PathVariable Long id) {
        EvenementDTO evenement = evenementService.getEvenementById(id);
        if (evenement != null) {
            return new ResponseEntity<>(evenement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<EvenementDTO> createEvenement(@RequestBody EvenementDTO evenementDTO) {
        EvenementDTO createdEvenement = evenementService.createEvenement(evenementDTO);
        return new ResponseEntity<>(createdEvenement, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EvenementDTO> updateEvenement(@PathVariable Long id, @RequestBody EvenementDTO evenementDTO) {
        EvenementDTO updatedEvenement = evenementService.updateEvenement(id, evenementDTO);
        if (updatedEvenement != null) {
            return new ResponseEntity<>(updatedEvenement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvenement(@PathVariable Long id) {
        System.out.println("Deleting event with ID: " + id);
        String deletionMessage = evenementService.deleteEvenement(id);
        System.out.println("Deletion message: " + deletionMessage);
        if (deletionMessage.startsWith("Evenement with ID") && deletionMessage.endsWith("successfully deleted.")) {
            // If the deletion message indicates success
            return new ResponseEntity<>(deletionMessage, HttpStatus.OK);
        } else {
            // If the deletion message indicates "Not Found" or other errors
            return new ResponseEntity<>(deletionMessage, HttpStatus.NOT_FOUND);
        }
    }


}
