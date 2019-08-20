package ir.maktab.service.course;

import ir.maktab.mapper.CourseMapper;
import ir.maktab.model.course.Course;
import ir.maktab.model.course.dto.CourseDto;
import ir.maktab.service.base.BaseService;

import java.util.Collection;
import java.util.Set;

public interface CourseService extends BaseService<Course, CourseDto, Long, CourseMapper> {

    CourseDto removeStudents(Long id, Set<Long> studentsId);

    CourseDto addStudents(Long id, Set<Long> studentsId);

    CourseDto setTeacher(Long id, Long teacherId);

    Collection<CourseDto> findAllByTeacherId(Long id);

    Collection<CourseDto> findAllByTeacherUsername(String username);

    Collection<CourseDto> findAllByStudentId(Long id);

}
