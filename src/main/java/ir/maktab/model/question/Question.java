package ir.maktab.model.question;

import ir.maktab.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class Question extends BaseEntity<Long> {

}
