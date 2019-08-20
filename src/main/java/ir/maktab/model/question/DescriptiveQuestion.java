package ir.maktab.model.question;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Polymorphism;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@NoArgsConstructor

@Entity
@Table(name = "descriptive_questions")
//@DiscriminatorValue("descriptive") // this is for SINGLE_TABLE
public class DescriptiveQuestion extends BaseQuestion {

    @Lob
    private String rightAnswer;

    public DescriptiveQuestion(String title) {
        super(title);
    }
}
