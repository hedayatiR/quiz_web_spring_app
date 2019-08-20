package ir.maktab.model.course;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.exam.Exam;
import ir.maktab.model.user.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "courses")
public class Course extends BaseEntity<Long> {

    private String name;

//    @Column(unique = true)
//    private Long code;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToMany
    @JoinTable(name = "courses_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students;

    @ManyToOne
    private User teacher;

    @OneToMany(mappedBy = "course")
    private Collection<Exam> exams;


    public Course(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @PreRemove
    private void removeStudentsFromCourse() {
        System.out.println("preremove of course");
        this.students = null;
    }


    public void addStudents(Set<User> studentsToAdd){
        if (this.students == null)
            this.students = new HashSet<>();
        this.students.addAll(studentsToAdd);
    }


    public void removeStudents(Set<Long> studentsIdToRemove){

        System.out.println(this.students);

        List<User> studentList = new ArrayList<>(this.students);

        for (Long idStudent:
                studentsIdToRemove) {

            for (int i = (studentList.size()-1); i >=0 ; i--) {
                if (studentList.get(i).getId() == idStudent){
                    studentList.remove(i);
                    break;
                }
            }
        }
        Set<User> targetSet = new HashSet<>(studentList);
        this.students = targetSet;

        System.out.println(this.students);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
