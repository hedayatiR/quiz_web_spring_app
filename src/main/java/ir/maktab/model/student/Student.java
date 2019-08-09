package ir.maktab.model.student;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.course.Course;
import ir.maktab.model.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Data

@Entity
@Table(name = "students")
public class Student extends BaseEntity<Long> {

    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    public Student(String firstName, String lastName, Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
    }

    @PreRemove
    private void removeStudentsFromCourse() {

        System.out.println("preremove of student");
        for (Course course : courses) {
            course.getStudents().remove(this);
        }
        this.courses = null;
    }

}
