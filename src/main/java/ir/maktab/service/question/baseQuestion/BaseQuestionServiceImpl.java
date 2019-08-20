package ir.maktab.service.question.baseQuestion;

import ir.maktab.mapper.question.BaseQuestionMapper;
import ir.maktab.model.exam.Exam;
import ir.maktab.model.question.BaseQuestion;
import ir.maktab.model.question.dto.QuestionFullDto;
import ir.maktab.repository.BaseQuestionRepository;
import ir.maktab.repository.ExamRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
@Transactional
public class BaseQuestionServiceImpl extends BaseServiceImpl<BaseQuestion, QuestionFullDto, Long,
        BaseQuestionRepository, BaseQuestionMapper>
        implements BaseQuestionService {

    @Autowired
    private ExamRepository examRepository;

    public BaseQuestionServiceImpl(BaseQuestionRepository repository, BaseQuestionMapper baseMapper) {
        super(repository, baseMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<QuestionFullDto> findQuestionsByExamId(Long examId) {
         Exam exam = examRepository.findByIdFetchQuestions(examId);
         if (exam ==null)
             return null;

        Collection<BaseQuestion> baseQuestions = exam.getQuestions();
         return baseMapper.entityToDtoCollection(baseQuestions, dtoClass);
    }
}
