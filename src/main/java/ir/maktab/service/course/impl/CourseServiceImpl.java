package ir.maktab.service.course.impl;

import ir.maktab.mapper.CourseMapper;
import ir.maktab.model.course.Course;
import ir.maktab.model.course.dto.CourseDto;
import ir.maktab.model.user.User;
import ir.maktab.repository.CourseRepository;
import ir.maktab.repository.UserRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.course.CourseService;
import ir.maktab.service.course.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceImpl<Course, CourseDto, Long, CourseRepository, CourseMapper>
        implements CourseService {


    @Autowired
    private UserRepository userRepository;

    public CourseServiceImpl(CourseRepository repository, CourseMapper baseMapper) {
        super(repository, baseMapper);
    }


    @Override
    public CourseDto setTeacher(Long id, Long teacherId) {
        // some validation
        User teacher = userRepository.findById(teacherId).get();

        if (teacher == null)
            throw new InvalidInputException("Teacher with this id not found");


        if (teacher.getAccount().getRole().getName() != "TEACHER"){
             throw new InvalidInputException("This user is not teacher");
        }

        Optional<Course> course = repository.findById(id);
        course.get().setTeacher(teacher);

        Course editedCourse = repository.save(course.get());

        return (baseMapper.entityToDto(editedCourse, CourseDto.class));
    }

    @Override
    public CourseDto removeStudents(Long id, Set<Long> studentsId) {
        Optional<Course> course = repository.findById(id);
        course.get().removeStudents(studentsId);

        Course editedCourse = repository.save(course.get());
        return (baseMapper.entityToDto(editedCourse, CourseDto.class));
    }


    @Override
    public CourseDto addStudents(Long id, Set<Long> studentsId) {
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

        Course editedCourse = repository.save(course.get());
        return (baseMapper.entityToDto(editedCourse, CourseDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CourseDto> findAllByTeacherId(Long id) {
        Set<Course> editedCourses = repository.findByTeacherId(id);
        return (baseMapper.entityToDtoCollection(editedCourses, CourseDto.class));
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<CourseDto> findAllByTeacherUsername(String username) {

        Set<Course> editedCourses = repository.findByTeacherAccountUsername(username);
        return (baseMapper.entityToDtoCollection(editedCourses, CourseDto.class));

    }


    @Override
    @Transactional(readOnly = true)
    public Collection<CourseDto> findAllByStudentId(Long id) {
        Set<Course> editedCourses = repository.findByStudentsId(id);
        return (baseMapper.entityToDtoCollection(editedCourses, CourseDto.class));
    }

}
