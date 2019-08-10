package ir.maktab.model.user;

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
@Table(name = "users")
public class User extends BaseEntity<Long> {

    private String firstName;
    private String lastName;

    private String email;


    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @ManyToMany(mappedBy = "students")
    private Set<Course> studentCourses;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> teacherCourses;

    public User(String firstName, String lastName, Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
    }

    @PreRemove
    private void removeStudentFromCourses() {


        if (!(studentCourses == null)) {
            System.out.println("studentCourses preremove of user");
            for (Course course : studentCourses) {
                course.getStudents().remove(this);
            }
            this.studentCourses = null;
        }

        if (!(teacherCourses == null)) {
            System.out.println("teacherCourses preremove of user");
            for (Course course : teacherCourses) {
                course.setTeacher(null);
            }
            this.teacherCourses = null;
        }


    }

}
