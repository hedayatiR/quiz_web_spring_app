package ir.maktab.model.teacher;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.course.Course;
import ir.maktab.model.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor


@Entity
@Table(name = "teachers")
public class Teacher extends BaseEntity<Long> {

    private String firstName;
    private String lastName;


//    @ManyToOne
//    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;


    public Teacher(String firstName, String lastName, Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
    }

    @PreRemove
    private void removeStudentsFromCourse() {
        System.out.println("preremove of teacher");
        for (Course course : courses) {
            course.setTeacher(null);
        }
        this.courses = null;
    }


}
