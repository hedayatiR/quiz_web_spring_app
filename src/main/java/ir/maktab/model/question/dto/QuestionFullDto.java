package ir.maktab.model.question.dto;

import ir.maktab.model.question.option.Option;
import lombok.Data;

import java.util.Set;

@Data

public class QuestionFullDto extends BaseQuestionDto {
    private String rightAnswer;

    private Set<Option> options;
    private Integer rightChoice;

}
