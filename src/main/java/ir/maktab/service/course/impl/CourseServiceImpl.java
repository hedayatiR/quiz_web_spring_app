package ir.maktab.service.course.impl;

import ir.maktab.model.course.Course;
import ir.maktab.model.user.User;
import ir.maktab.repository.CourseRepository;
import ir.maktab.repository.UserRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.course.CourseService;
import ir.maktab.service.course.exception.InvalidInputException;
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
    private UserRepository userRepository;


    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
    }


    @Override
    public Course setTeacher(Long id, Long teacherId) {
        // some validation
        User teacher = userRepository.findById(teacherId).get();

        if (teacher == null)
            throw new InvalidInputException("Teacher with this id not found");


        if (teacher.getAccount().getRole().getName() != "TEACHER"){
             throw new InvalidInputException("This user is not teacher");
        }

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
            Set<User> students = new HashSet<>();
            for (Long studentId :
                    studentsId) {
                students.add(userRepository.findById(studentId).get());
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
        return repository.findByTeacherAccountUsername(username);
    }


    @Override
    @Transactional(readOnly = true)
    public Set<Course> findAllByStudentId(Long id) {
        return repository.findByStudentsId(id);
    }

}
