package com.Marketing.MarketingAPI.controllers;

import com.Marketing.MarketingAPI.DTO.CampaignDTO;
import com.Marketing.MarketingAPI.DTO.ClientDTO;
import com.Marketing.MarketingAPI.models.Client;
import com.Marketing.MarketingAPI.services.CampaignService;
import com.Marketing.MarketingAPI.services.QuestionnaireService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/campaign")
public class CampaignController {
    public final CampaignService campaignService;
    private final QuestionnaireService questionnaireService;
    @GetMapping("/all")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns(){
        return new ResponseEntity<>(campaignService.getAllCampaigns(), HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CampaignDTO> getCampaignById(@PathVariable("id") Long id){
        return new ResponseEntity<>(campaignService.getCampaignById(id),HttpStatus.OK);
    }
    @GetMapping("/get-by-questionnaire/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CampaignDTO> getCampaignByQuestionnaire(@PathVariable("id") Long id) {
        try {
            CampaignDTO campaignDTO = questionnaireService.getCampaignByQuestionnaireId(id);
            return ResponseEntity.ok(campaignDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CampaignDTO> addCampaignDraft(@RequestBody CampaignDTO campaignDTO ){
        return new ResponseEntity<>(campaignService.addCampaignDraft(campaignDTO),HttpStatus.CREATED);
    }
    @PostMapping("/{id}/addClients")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CampaignDTO> addClientsToCampaign(@PathVariable("id")Long id,@RequestBody List<ClientDTO> clients){
        return new ResponseEntity<>(campaignService.addClientsToCampaign(id,clients),HttpStatus.CREATED);
    }
    @PostMapping("/{id}/launch")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CampaignDTO> launchCampaign(@PathVariable("id")Long id) throws MessagingException {
        return new ResponseEntity<>(campaignService.lauchCampaign(id),HttpStatus.CREATED);
    }
}
