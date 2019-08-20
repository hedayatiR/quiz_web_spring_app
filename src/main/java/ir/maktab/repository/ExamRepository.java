package ir.maktab.repository;

import ir.maktab.model.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Set<Exam> findByCourse_Id(Long courseId);

    @Query("select e from Exam e left join fetch e.questions where e.id = :examId")
    Exam findByIdFetchQuestions(@Param("examId") Long examId);
}
