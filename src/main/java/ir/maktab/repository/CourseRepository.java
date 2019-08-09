package ir.maktab.repository;

import ir.maktab.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Course findByCode(Long code);

    Set<Course> findByTeacherId(Long id);
    Set<Course> findByTeacherUserUsername(String username);
    Set<Course> findByStudentsId(Long id);
}
