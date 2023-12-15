package com.Marketing.MarketingAPI.services;

import com.Marketing.MarketingAPI.DTO.*;
import com.Marketing.MarketingAPI.models.*;
import com.Marketing.MarketingAPI.repositories.CampaignRepo;
import com.Marketing.MarketingAPI.repositories.ClientRepo;
import com.Marketing.MarketingAPI.repositories.QuestionRepo;
import com.Marketing.MarketingAPI.repositories.QuestionnaireRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionnaireService {
    private final QuestionnaireRepo questionnaireRepo;
    private final QuestionRepo questionRepo;
    private final CampaignRepo campaignRepo;
    private final ModelMapper modelMapper;
    public List<QuestionnaireDTO> getAllQuestionnaires() {
        List<Questionnaire> questionnaires = questionnaireRepo.findAll();
        return questionnaires.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public QuestionnaireDTO getQuestionnaieById(Long id) {
        Questionnaire questionnaire = questionnaireRepo.findById(id).get();
        return convertToDto(questionnaire);
    }

    /*public QuestionnaireDTO addQuestionnaire(QuestionnaireDTO questionnaireDTO) {
        Questionnaire questionnaire = modelMapper.map(questionnaireDTO, Questionnaire.class);
        Campaign campaign = campaignRepo.findById(questionnaireDTO.getCampaign_id())
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + questionnaireDTO.getCampaign_id()));
        questionnaire.setCampaign(campaign);
        Questionnaire savedQuestionnaire = questionnaireRepo.save(questionnaire);
        return convertToDto(savedQuestionnaire);
    }*/
    public QuestionnaireDTO addQuestionnaire(QuestionnaireDTO questionnaireDTO) {
        Campaign campaign = campaignRepo.findById(questionnaireDTO.getCampaign_id())
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with id: " + questionnaireDTO.getCampaign_id()));
        Questionnaire questionnaire = modelMapper.map(questionnaireDTO, Questionnaire.class);
        questionnaire.setCampaign(campaign);
        List<Question> questions = questionnaireDTO.getQuestions().stream().map(q->modelMapper.map(q, Question.class)).collect(Collectors.toList());
        for (Question question : questions) {
            question.setQuestionnaire(questionnaire);
        }
        questionnaire.setQuestions(questions);
        Questionnaire savedQuestionnaire = questionnaireRepo.save(questionnaire);
        return convertToDto(savedQuestionnaire);
    }
    public QuestionnaireDTO getQuestionnaireByCampaignId(Long campaignId) {
        Questionnaire questionnaire = questionnaireRepo.findByCampaignId(campaignId)
                .orElseThrow(() -> new EntityNotFoundException("Questionnaire not found for Campaign ID: " + campaignId));
        return convertToDto(questionnaire);
    }
    public CampaignDTO getCampaignByQuestionnaireId(Long questionnaireId) {
        Questionnaire questionnaire = questionnaireRepo.findById(questionnaireId)
                .orElseThrow(() -> new EntityNotFoundException("Questionnaire not found with id: " + questionnaireId));

        Campaign campaign = questionnaire.getCampaign();

        if (campaign == null) {
            throw new EntityNotFoundException("Campaign not found for Questionnaire ID: " + questionnaireId);
        }

        return convertCampaignToDto(campaign);
    }
    public QuestionnaireDTO convertToDto(Questionnaire questionnaire) {
        QuestionnaireDTO questionnaireDTO = modelMapper.map(questionnaire, QuestionnaireDTO.class);
        if (questionnaire.getCampaign() != null) {
            questionnaireDTO.setCampaign_id(questionnaire.getCampaign().getId());
        }

        List<QuestionDTO> questionDTOs = questionnaire.getQuestions().stream()
                .map(this::convertQuestionToDto)
                .collect(Collectors.toList());

        questionnaireDTO.setQuestions(questionDTOs);

        return questionnaireDTO;
    }
    private QuestionDTO convertQuestionToDto(Question question) {
        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
        questionDTO.setQuestionnaire_id(question.getQuestionnaire().getId());
        List<ResponseDTO> responseDTOs = question.getResponses().stream()
                .map(response -> modelMapper.map(response, ResponseDTO.class))
                .collect(Collectors.toList());

        questionDTO.setResponses(responseDTOs);
        return questionDTO;
    }
    private CampaignDTO convertCampaignToDto(Campaign campaign) {
        return modelMapper.map(campaign, CampaignDTO.class);
    }


}
