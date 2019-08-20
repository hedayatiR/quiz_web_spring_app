package ir.maktab.model.exam;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.course.Course;
import ir.maktab.model.question.BaseQuestion;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor

@Entity
@Table(name = "exams")
public class Exam extends BaseEntity<Long> {
    private String title;
    private String description;
    private int duration;

    @Temporal(TemporalType.DATE)
    private Date takeDate;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<BaseQuestion> questions;

    @ManyToOne
    private Course course;

    public Exam(String title) {
        this.title = title;
    }

    public Exam(String title, String description, int duration, Date takeDate) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.takeDate = takeDate;
    }

    public void addQuestion(BaseQuestion question){
        if (questions == null)
            questions = new HashSet<>();
        questions.add(question);
    }
}

