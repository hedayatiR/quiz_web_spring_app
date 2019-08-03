package ir.maktab.service.course;

import ir.maktab.model.course.Course;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.service.base.BaseService;

import java.util.Set;

public interface CourseService extends BaseService<Course, Long> {

    Course removeStudents(Long id, Set<Student> students);

    Course addStudents(Long id, Set<Student> students);

    Course setTeacher(Long id, Teacher teacher);

    Set<Course> findAllByTeacherId(Long id);

    Set<Course> findAllByStudentId(Long id);

}
