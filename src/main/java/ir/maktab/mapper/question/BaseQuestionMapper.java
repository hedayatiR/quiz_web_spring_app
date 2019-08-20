package ir.maktab.mapper.question;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.question.BaseQuestion;
import ir.maktab.model.question.DescriptiveQuestion;
import ir.maktab.model.question.dto.BaseQuestionDto;
import ir.maktab.model.question.dto.DescriptiveQuestionDto;
import ir.maktab.model.question.dto.QuestionFullDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class BaseQuestionMapper extends BaseMapperImpl<BaseQuestion, QuestionFullDto> {

    public BaseQuestionMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
