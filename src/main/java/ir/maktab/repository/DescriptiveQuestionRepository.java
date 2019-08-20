package ir.maktab.repository;

import ir.maktab.model.question.DescriptiveQuestion;
import ir.maktab.model.question.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptiveQuestionRepository extends JpaRepository<DescriptiveQuestion, Long> {
}
