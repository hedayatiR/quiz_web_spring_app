package ir.maktab.service.question.baseQuestion;

import ir.maktab.mapper.question.BaseQuestionMapper;
import ir.maktab.model.question.BaseQuestion;
import ir.maktab.model.question.dto.QuestionFullDto;
import ir.maktab.service.base.BaseService;

import java.util.Collection;

public interface BaseQuestionService extends  BaseService<BaseQuestion, QuestionFullDto,
        Long, BaseQuestionMapper>{


    Collection<QuestionFullDto> findQuestionsByExamId(Long examId);
}
