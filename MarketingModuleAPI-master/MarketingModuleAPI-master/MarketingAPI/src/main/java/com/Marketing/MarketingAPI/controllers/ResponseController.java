package com.Marketing.MarketingAPI.controllers;

import com.Marketing.MarketingAPI.DTO.ResponseDTO;
import com.Marketing.MarketingAPI.services.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/response")
public class ResponseController {
    private final ResponseService responseService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addResponseToQuestion(
            @RequestBody ResponseDTO responseDTO
    ) {
        ResponseDTO savedResponse = responseService.addResponseToQuestion(responseDTO);
        return new ResponseEntity<>(savedResponse, HttpStatus.CREATED);
    }
}
