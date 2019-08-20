package ir.maktab.service.exam.impl;

import ir.maktab.exceptionHandler.UnauthorizedException;
import ir.maktab.mapper.ExamMapper;
import ir.maktab.model.course.Course;
import ir.maktab.model.exam.Exam;
import ir.maktab.model.exam.dto.ExamDto;
import ir.maktab.repository.CourseRepository;
import ir.maktab.repository.ExamRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.exam.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
@Transactional
public class ExamServiceImpl extends BaseServiceImpl<Exam, ExamDto, Long, ExamRepository, ExamMapper> implements ExamService {

    @Autowired
    CourseRepository courseRepository;

    public ExamServiceImpl(ExamRepository repository, ExamMapper baseMapper) {
        super(repository, baseMapper);
    }


    @Override
    public Collection<ExamDto> findByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).get();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!(course.getTeacher().getAccount().getUsername().equals(username)))
            throw new UnauthorizedException("This is not your course. You can see just your course's exams!");

        Set<Exam> exams = repository.findByCourse_Id(courseId);

        return baseMapper.entityToDtoCollection(exams, ExamDto.class);

    }
}
