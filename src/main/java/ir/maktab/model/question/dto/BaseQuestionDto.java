package ir.maktab.model.question.dto;

import ir.maktab.model.base.BaseDTO;
import lombok.Data;

@Data

public class BaseQuestionDto extends BaseDTO<Long> {

    private String title;
    private String type;
    private String questionContent;

}