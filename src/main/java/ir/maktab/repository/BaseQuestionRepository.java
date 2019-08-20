package ir.maktab.repository;

import ir.maktab.model.question.BaseQuestion;
import ir.maktab.model.question.DescriptiveQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseQuestionRepository extends JpaRepository<BaseQuestion, Long> {
}
