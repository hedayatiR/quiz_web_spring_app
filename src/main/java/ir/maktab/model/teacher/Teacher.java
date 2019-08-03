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

    private UserStatusEnum status;

    @ManyToOne
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;


    public Teacher(String firstName, String lastName, Role role, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.user = user;
        this.status = UserStatusEnum.INACTIVATED;
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
