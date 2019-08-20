package ir.maktab.model.question.dto;

import ir.maktab.model.base.BaseDTO;
import lombok.Data;

@Data

public class DescriptiveQuestionDto extends BaseQuestionDto {
    private String rightAnswer;
}
