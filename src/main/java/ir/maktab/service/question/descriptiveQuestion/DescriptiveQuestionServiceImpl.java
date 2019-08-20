package ir.maktab.service.question.descriptiveQuestion;

import ir.maktab.mapper.question.DescriptiveQuestionMapper;
import ir.maktab.model.question.DescriptiveQuestion;
import ir.maktab.model.question.TestQuestion;
import ir.maktab.model.question.dto.DescriptiveQuestionDto;
import ir.maktab.repository.DescriptiveQuestionRepository;
import ir.maktab.repository.TestQuestionRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DescriptiveQuestionServiceImpl extends BaseServiceImpl<DescriptiveQuestion, DescriptiveQuestionDto, Long,
        DescriptiveQuestionRepository, DescriptiveQuestionMapper>
    implements DescriptiveQuestionService {

    public DescriptiveQuestionServiceImpl(DescriptiveQuestionRepository repository, DescriptiveQuestionMapper baseMapper) {
        super(repository, baseMapper);
    }

}
