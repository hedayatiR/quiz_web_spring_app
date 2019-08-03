package ir.maktab.model.student;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.course.Course;
import ir.maktab.model.role.Role;
import ir.maktab.model.user.User;
import ir.maktab.model.user.UserStatusEnum;
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

    private UserStatusEnum status;

    @ManyToOne
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    public Student(String firstName, String lastName, Role role, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.user = user;
        this.status = UserStatusEnum.INACTIVATED;
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
