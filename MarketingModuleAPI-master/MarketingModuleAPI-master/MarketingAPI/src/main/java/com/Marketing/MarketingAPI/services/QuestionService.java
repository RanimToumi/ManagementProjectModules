package com.Marketing.MarketingAPI.services;

import com.Marketing.MarketingAPI.DTO.QuestionDTO;
import com.Marketing.MarketingAPI.DTO.ResponseDTO;
import com.Marketing.MarketingAPI.models.Question;
import com.Marketing.MarketingAPI.models.Response;
import com.Marketing.MarketingAPI.repositories.QuestionRepo;
import com.Marketing.MarketingAPI.repositories.ResponseRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final ResponseRepo responseRepo;
    private final QuestionRepo questionRepo;
    private final ModelMapper modelMapper;

    public QuestionDTO getQuestionWithResponses(Long questionId) {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);

        List<Response> responses = responseRepo.findByQuestionId(questionId);
        List<ResponseDTO> responseDTOs = responses.stream()
                .map(response -> modelMapper.map(response, ResponseDTO.class))
                .collect(Collectors.toList());

        questionDTO.setResponses(responseDTOs);
        return questionDTO;
    }
}
