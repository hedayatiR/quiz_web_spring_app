package ir.maktab.repository;

import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.user.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Set<Teacher> findByCoursesId(Long id);
    Set<Teacher> findByUserEnabled(boolean enabled);
}
