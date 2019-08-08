package ir.maktab.model.teacher;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.course.Course;
import ir.maktab.model.role.Role;
import ir.maktab.model.user.User;
import ir.maktab.model.user.UserStatusEnum;
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
    private User user;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;


    public Teacher(String firstName, String lastName, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
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
