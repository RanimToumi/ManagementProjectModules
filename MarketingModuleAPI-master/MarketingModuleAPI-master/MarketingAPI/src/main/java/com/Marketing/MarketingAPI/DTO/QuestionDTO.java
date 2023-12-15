package com.Marketing.MarketingAPI.DTO;
import com.Marketing.MarketingAPI.models.Questionnaire;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
public class QuestionDTO {
    private Long id;
    private String questionText;
    private List<String> options;
    private Long questionnaire_id;
    private List<ResponseDTO> responses;
}
