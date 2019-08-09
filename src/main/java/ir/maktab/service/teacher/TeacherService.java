package ir.maktab.service.teacher;

import ir.maktab.model.teacher.Teacher;
import ir.maktab.service.base.BaseService;

import java.util.Set;

public interface TeacherService extends BaseService<Teacher, Long> {

    Teacher changeStatus(Long id);

    Set<Teacher> findByCourseId(Long id);

    Set<Teacher> findActivatedTeachers();
}
