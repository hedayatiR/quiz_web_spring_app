package ir.maktab.service.exam;

import ir.maktab.mapper.ExamMapper;
import ir.maktab.model.exam.Exam;
import ir.maktab.model.exam.dto.ExamDto;
import ir.maktab.service.base.BaseService;

import java.util.Collection;

public interface ExamService extends BaseService<Exam, ExamDto, Long, ExamMapper> {

    Collection<ExamDto> findByCourseId(Long courseId);

}
