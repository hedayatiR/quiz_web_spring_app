package ir.maktab.service.course.impl;

import ir.maktab.model.course.Course;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.repository.CourseRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.course.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository>
        implements CourseService {


    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
    }


    @Override
    public Course setTeacher(Long id, Teacher teacher){
        // some validation
//        if (teacher.getRole().getName() != RoleEnum.TEACHER){
//            // throw exception
//            return;
//        }
        Optional<Course> course = repository.findById(id);
        course.get().setTeacher(teacher);

        return repository.save(course.get());
    }

    @Override
    public Course removeStudents(Long id, Set<Student> students) {
        Optional<Course> course = repository.findById(id);
        course.get().removeStudents(students);

        return repository.save(course.get());
    }


    @Override
    public Course addStudents(Long id, Set<Student> students){
        // some validation
        Optional<Course> course = repository.findById(id);
        course.get().addStudents(students);

        return repository.save(course.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findAllByTeacherId(Long id) {
        return repository.findByTeacherId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findAllByStudentId(Long id) {
        return repository.findByStudentsId(id);
    }

}
