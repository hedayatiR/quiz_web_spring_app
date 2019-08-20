package ir.maktab.model.question.option;

import ir.maktab.model.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@NoArgsConstructor

@Entity
@Table(name = "options")
public class Option extends BaseEntity<Long> {

    @Lob
    private String optionContent;

    public Option(String optionContent) {
        this.optionContent = optionContent;
    }
}
