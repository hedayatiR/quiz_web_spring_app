package ir.maktab.service.student;

import ir.maktab.model.student.Student;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.service.base.BaseService;

import java.util.Set;

public interface StudentService extends BaseService<Student, Long> {

    void changeStatus(Student student, UserStatusEnum status);

    Set<Student> findAllByCourseId(Long id);


}
