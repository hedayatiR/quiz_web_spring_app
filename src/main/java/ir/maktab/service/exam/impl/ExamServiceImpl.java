package ir.maktab.service.exam.impl;

import ir.maktab.model.exam.Exam;
import ir.maktab.repository.ExamRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.exam.ExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExamServiceImpl extends BaseServiceImpl<Exam, Long, ExamRepository> implements ExamService {
    public ExamServiceImpl(ExamRepository repository) {
        super(repository);
    }
}
