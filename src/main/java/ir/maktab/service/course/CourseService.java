package ir.maktab.service.course;

import ir.maktab.model.course.Course;
import ir.maktab.model.user.User;
import ir.maktab.service.base.BaseService;

import java.util.Set;

public interface CourseService extends BaseService<Course, Long> {

    Course removeStudents(Long id, Set<Long> studentsId);

    Course addStudents(Long id, Set<Long> studentsId);

    Course setTeacher(Long id, Long teacherId);

    Set<Course> findAllByTeacherId(Long id);

    Set<Course> findAllByTeacherUsername(String username);

    Set<Course> findAllByStudentId(Long id);

}
