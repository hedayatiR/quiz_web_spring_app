package ir.maktab.service.student.impl;

import ir.maktab.model.course.Course;
import ir.maktab.model.student.Student;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.repository.StudentRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.student.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentRepository>
    implements StudentService {

    public StudentServiceImpl(StudentRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    public void changeStatus(Student student, UserStatusEnum status){
        // some validation
        student.getUser().setStatus(status);
    }

    @Override
    public Set<Student> findAllByCourseId(Long id) {
        return repository.findByCoursesId(id);
    }


}
