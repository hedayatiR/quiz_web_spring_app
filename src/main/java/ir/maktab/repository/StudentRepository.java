package ir.maktab.repository;

import ir.maktab.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Set<Student> findByCoursesId(Long id);
}
