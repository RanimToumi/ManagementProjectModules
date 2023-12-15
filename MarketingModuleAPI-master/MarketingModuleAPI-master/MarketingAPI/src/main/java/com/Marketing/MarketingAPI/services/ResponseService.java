package com.Marketing.MarketingAPI.services;

import com.Marketing.MarketingAPI.DTO.ClientDTO;
import com.Marketing.MarketingAPI.DTO.ResponseDTO;
import com.Marketing.MarketingAPI.models.Client;
import com.Marketing.MarketingAPI.models.Question;
import com.Marketing.MarketingAPI.models.Response;
import com.Marketing.MarketingAPI.repositories.QuestionRepo;
import com.Marketing.MarketingAPI.repositories.ResponseRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepo responseRepo;
    private final QuestionRepo questionRepo;
    private final ModelMapper modelMapper;

    public ResponseDTO addResponseToQuestion(ResponseDTO responseDTO) {
        Long questionId = responseDTO.getQuestion_id();
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        Response response = modelMapper.map(responseDTO, Response.class);
        response.setQuestion(question);

        Response savedResponse = responseRepo.save(response);
        return convertToDto(savedResponse);
    }
    private ResponseDTO convertToDto(Response response) {
        ResponseDTO responseDTO = modelMapper.map(response, ResponseDTO.class);
        if (response.getQuestion() != null) {
            responseDTO.setQuestion_id(response.getQuestion().getId());
        }
        return responseDTO;
    }
}
