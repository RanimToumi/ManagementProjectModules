package com.Marketing.MarketingAPI.controllers;
import com.Marketing.MarketingAPI.DTO.FieldOfActivityDTO;
import com.Marketing.MarketingAPI.services.FieldOfActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/field")
public class FieldController {
    public final FieldOfActivityService fieldService;
    @PostMapping("/add")
    public ResponseEntity<FieldOfActivityDTO> addField(@RequestBody FieldOfActivityDTO fieldDTO){
        return new ResponseEntity<>(fieldService.addField(fieldDTO), HttpStatus.CREATED);
    }
}
