package ir.maktab.model.course;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "courses")
public class Course extends BaseEntity<Long> {

    private String name;

    @Column(unique = true)
    private Long code;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToMany
    private Set<Student> students;

    @ManyToOne
    private Teacher teacher;

    public Course(String name, Long code, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @PreRemove
    private void removeStudentsFromCourse() {

        System.out.println("preremove of course");
//        for (Student student : students) {
//            u.getGroups().remove(this);
//        }
        this.students = null;
    }


    public void addStudents(Set<Student> studentsToAdd){
        if (this.students == null)
            this.students = new HashSet<>();
        this.students.addAll(studentsToAdd);
    }


    public void removeStudents(Set<Student> studentsToRemove){
        this.students.removeAll(studentsToRemove);
        for (Student student:
             studentsToRemove) {
            this.students.remove(student);
        }

        System.out.println(this.students);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
