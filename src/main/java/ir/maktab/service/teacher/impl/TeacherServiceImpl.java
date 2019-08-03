package ir.maktab.service.teacher.impl;

import ir.maktab.model.course.Course;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.repository.TeacherRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.teacher.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional

public class TeacherServiceImpl extends BaseServiceImpl<Teacher, Long, TeacherRepository>
        implements TeacherService {

    public TeacherServiceImpl(TeacherRepository repository) {
        super(repository);
    }

    @Override
    public void changeStatus(Teacher teacher, UserStatusEnum status) {
        // some validation
        teacher.setStatus(status);
    }

    @Override
    public Set<Teacher> findByCourseId(Long id) {
        return repository.findByCoursesId(id);
    }

}
