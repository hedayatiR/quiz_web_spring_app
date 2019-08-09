package ir.maktab.service.course.impl;

import ir.maktab.model.course.Course;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.repository.CourseRepository;
import ir.maktab.repository.StudentRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository>
        implements CourseService {

    @Autowired
    private StudentRepository studentRepository;


    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
    }


    @Override
    public Course setTeacher(Long id, Teacher teacher) {
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
    public Course removeStudents(Long id, Set<Long> studentsId) {
        Optional<Course> course = repository.findById(id);
        course.get().removeStudents(studentsId);

        return repository.save(course.get());
    }


    @Override
    public Course addStudents(Long id, Set<Long> studentsId) {
        // some validation
        Optional<Course> course = repository.findById(id);

        if (studentsId.size() < 1)
            course.get().setStudents(null);

        else {
            Set<Student> students = new HashSet<>();
            for (Long studentId :
                    studentsId) {
                students.add(studentRepository.findById(studentId).get());
            }
            course.get().setStudents(students);
        }

        return repository.save(course.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findAllByTeacherId(Long id) {
        return repository.findByTeacherId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findAllByTeacherUsername(String username) {
        return repository.findByTeacherUserUsername(username);
    }


    @Override
    @Transactional(readOnly = true)
    public Set<Course> findAllByStudentId(Long id) {
        return repository.findByStudentsId(id);
    }

}
