package com.ordering.procurementFlow.controllers;

import com.ordering.procurementFlow.DTO.ProviderDto;
import com.ordering.procurementFlow.services.ProviderService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;
    @GetMapping("/all")
    public ResponseEntity<List<ProviderDto>> getAllProvider(){
        List<ProviderDto> providerDto= providerService.findAllProviders();
        return new ResponseEntity<>(providerDto, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProviderDto> getProviderById( @PathVariable("id")Long providerId){
        Optional<ProviderDto> providerDto= providerService.findProviderById(providerId);
        if (providerDto.isPresent()) {
            return new ResponseEntity<>(providerDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    @PostMapping("/add")
    //@PreAuthorize("hasRole('ADMIN')")
    //User doit être désérialisé (Conversion d'un objet Json ou xml en objet Java )
    public  ResponseEntity<ProviderDto> addProvider(@RequestBody ProviderDto providerDto, HttpServletResponse response){
        ProviderDto newProvider =providerService.addProvider(providerDto)  ;
        return new ResponseEntity<>(newProvider,HttpStatus.CREATED );
    }

    @PutMapping("/update")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProviderDto> UpdateProvider(@RequestBody ProviderDto providerDto , HttpServletResponse response) {
        ProviderDto updateProvider = providerService.UpdateProvider(providerDto);
        return new ResponseEntity<>(updateProvider, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> DeleteProvider(@PathVariable("id") Long id){
        try {
            providerService.DeleteProviderById(id);
            return new ResponseEntity<>("Fournisseur supprimé avec succès", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la suppression du fournisseur", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
