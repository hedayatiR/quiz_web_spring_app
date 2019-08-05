package ir.maktab.repository;

import ir.maktab.model.student.Student;
import ir.maktab.model.user.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Set<Student> findByCoursesId(Long id);
    Set<Student> findByUserStatus(UserStatusEnum status);
}
