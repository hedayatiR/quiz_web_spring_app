package ir.maktab.mapper.question;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.question.DescriptiveQuestion;
import ir.maktab.model.question.TestQuestion;
import ir.maktab.model.question.dto.DescriptiveQuestionDto;
import ir.maktab.model.question.dto.TestQuestionDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class DescriptiveQuestionMapper extends BaseMapperImpl<DescriptiveQuestion, DescriptiveQuestionDto> {

    public DescriptiveQuestionMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
