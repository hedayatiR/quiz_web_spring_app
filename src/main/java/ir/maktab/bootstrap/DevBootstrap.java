package ir.maktab.bootstrap;

import ir.maktab.model.course.Course;
import ir.maktab.model.role.Role;
import ir.maktab.model.role.RoleEnum;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.user.User;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.repository.CourseRepository;
import ir.maktab.repository.RoleRepository;
import ir.maktab.repository.StudentRepository;
import ir.maktab.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private RoleRepository roleRepository;
    private CourseRepository courseRepository;

    @Autowired
    public DevBootstrap(StudentRepository studentRepository, TeacherRepository teacherRepository, RoleRepository roleRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();

    }

    private void initData() {
        Role roleStudent = new Role(RoleEnum.STUDENT);
        roleRepository.save(roleStudent);
        Role roleTeacher = new Role(RoleEnum.TEACHER);
        roleRepository.save(roleTeacher);
        Role roleAdmin = new Role(RoleEnum.ADMIN);
        roleRepository.save(roleAdmin);


        User user1 = new User("u1", "p1");
        Student student1 = new Student("fn1", "ln1",roleStudent, user1);
        student1.getUser().setStatus(UserStatusEnum.ACTIVATED);
        studentRepository.save(student1);

        User user2 = new User("u2", "p2");
        Student student2 = new Student("fn2", "ln2",roleStudent, user2);
        student2.getUser().setStatus(UserStatusEnum.ACTIVATED);
        studentRepository.save(student2);

        User user5 = new User("u5", "p5");
        Teacher teacher1 = new Teacher("fn5", "ln5",roleTeacher, user5);
        teacher1.getUser().setStatus(UserStatusEnum.ACTIVATED);
        teacherRepository.save(teacher1);

        User user6 = new User("u6", "p6");
        Teacher teacher2 = new Teacher("fn6", "ln6",roleTeacher, user6);
        teacherRepository.save(teacher2);

        Course course1 = new Course("c1", 1L,
                new Date(2019,10,10),
                new Date(2019,10,12));
        course1.setTeacher(teacher1);
        Set<Student> students1 = new HashSet<>();
        students1.add(student1);
        course1.setStudents(students1);
        courseRepository.save(course1);


        Course course2 = new Course("c2", 2L,
                new Date(2021,10,10),
                new Date(2021,10,12));
        course2.setTeacher(teacher1);
        Set<Student> students2 = new HashSet<>();
        students2.add(student1);
        students2.add(student2);
        course2.setStudents(students2);
        courseRepository.save(course2);

    }
}


