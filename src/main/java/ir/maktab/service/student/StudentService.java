package ir.maktab.service.student;

import ir.maktab.model.student.Student;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.service.base.BaseService;

import java.util.Set;

public interface StudentService extends BaseService<Student, Long> {

    Student changeStatus(Long id);

    Set<Student> findAllByCourseId(Long id);

    Set<Student> findActivatedStudents();
}
