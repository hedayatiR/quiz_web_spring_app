package ir.maktab.model.question;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.course.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
//@NoArgsConstructor

@Entity
@Table(name = "base_questions")
@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "question_type")
public abstract class BaseQuestion extends BaseEntity<Long> {

    private String title;

    @Lob
    private String questionContent;

    @ManyToOne
    private Course course;

    private String type = this.getClass().getSimpleName();

    public BaseQuestion(String title) {
        this.title = title;
    }

    public BaseQuestion() {
    }
}
