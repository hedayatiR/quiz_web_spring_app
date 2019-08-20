package ir.maktab.mapper.question;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.account.Account;
import ir.maktab.model.account.dto.AccountDto;
import ir.maktab.model.question.TestQuestion;
import ir.maktab.model.question.dto.TestQuestionDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class TestQuestionMapper extends BaseMapperImpl<TestQuestion, TestQuestionDto> {

    public TestQuestionMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
