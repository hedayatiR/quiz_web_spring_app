package ir.maktab.model.question;

import ir.maktab.model.question.option.Option;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor

@Entity
@Table(name = "test_questions")
//@DiscriminatorValue("test") // this is for SINGLE_TABLE
public class TestQuestion extends BaseQuestion {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
    fetch = FetchType.EAGER)
    @JoinColumn(name = "test_question_id")
    private Set<Option> options;

    private Integer rightChoice;

    public TestQuestion(String title) {
        super(title);
    }
}
