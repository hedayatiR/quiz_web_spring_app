package ir.maktab.service.question.testQuestion;

import ir.maktab.mapper.question.TestQuestionMapper;
import ir.maktab.model.question.TestQuestion;
import ir.maktab.model.question.dto.TestQuestionDto;
import ir.maktab.repository.TestQuestionRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestQuestionServiceImpl extends BaseServiceImpl<TestQuestion, TestQuestionDto, Long,
        TestQuestionRepository, TestQuestionMapper>
    implements TestQuestionService{

    public TestQuestionServiceImpl(TestQuestionRepository repository, TestQuestionMapper baseMapper) {
        super(repository, baseMapper);
    }

}
