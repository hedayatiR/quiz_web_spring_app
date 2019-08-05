package ir.maktab.service.teacher;

import ir.maktab.model.course.Course;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.service.base.BaseService;

import java.util.List;
import java.util.Set;

public interface TeacherService extends BaseService<Teacher, Long> {

    void changeStatus(Teacher teacher, UserStatusEnum status);

    Set<Teacher> findByCourseId(Long id);

    Set<Teacher> findActivatedTeachers();
}
