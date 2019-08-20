package ir.maktab.service.question.testQuestion;

import ir.maktab.mapper.question.TestQuestionMapper;
import ir.maktab.model.question.TestQuestion;
import ir.maktab.model.question.dto.TestQuestionDto;
import ir.maktab.service.base.BaseService;

public interface TestQuestionService extends BaseService<TestQuestion, TestQuestionDto, Long, TestQuestionMapper>  {
}
