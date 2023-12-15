package com.Marketing.MarketingAPI.controllers;
import com.Marketing.MarketingAPI.DTO.QuestionDTO;
import com.Marketing.MarketingAPI.DTO.QuestionnaireDTO;
import com.Marketing.MarketingAPI.services.QuestionService;
import com.Marketing.MarketingAPI.services.ResponseService;
import com.Marketing.MarketingAPI.services.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;
    private final ResponseService responseService;
    private final QuestionService questionService;
    @GetMapping("/all")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<QuestionnaireDTO>> getAllQuestionnaires(){
        return new ResponseEntity<>(questionnaireService.getAllQuestionnaires(), HttpStatus.OK);
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<QuestionnaireDTO> addQuestionnaire(@RequestBody QuestionnaireDTO questionnaireDTO){
        return new ResponseEntity<>(questionnaireService.addQuestionnaire(questionnaireDTO),HttpStatus.CREATED);
    }
    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<QuestionnaireDTO> getQuestionnaireByCampaignId(@PathVariable("campaignId") Long campaignId) {
        QuestionnaireDTO questionnaire = questionnaireService.getQuestionnaireByCampaignId(campaignId);
        return ResponseEntity.ok(questionnaire);
    }
    @GetMapping("/question/{id}/responses")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<QuestionDTO> getQuestionWithResponses(@PathVariable("id") Long id) {
        return new ResponseEntity<>(questionService.getQuestionWithResponses(id),HttpStatus.OK);
    }
}
