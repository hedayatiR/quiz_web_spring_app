package ir.maktab.model.exam;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.question.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

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

    @OneToMany(cascade = {CascadeType.PERSIST})
    private Collection<Question> questions;
}

