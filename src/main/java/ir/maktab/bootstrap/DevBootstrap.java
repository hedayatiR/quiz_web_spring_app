package ir.maktab.bootstrap;

import ir.maktab.model.course.Course;
import ir.maktab.model.role.Role;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.user.User;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private RoleRepository roleRepository;
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Autowired
    public DevBootstrap(StudentRepository studentRepository, TeacherRepository teacherRepository, RoleRepository roleRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();

    }

    private void initData() {

        Role roleStudent = new Role("STUDENT");
        roleRepository.save(roleStudent);
        Role roleTeacher = new Role("TEACHER");
        roleRepository.save(roleTeacher);
        Role roleAdmin = new Role("ADMIN");
        roleRepository.save(roleAdmin);




        Collection<Role> rolesStudent = new HashSet<>();
        rolesStudent.add(roleStudent);

        Collection<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(roleAdmin);

        // admin
        User admin = new User("admin", bCryptPasswordEncoder.encode("1"), rolesAdmin);
        admin.setEnabled(true);
        userRepository.save(admin);

        // student 1
        User user1 = new User("reza", bCryptPasswordEncoder.encode("1"), rolesStudent);
        user1.setEnabled(true);
        Student student1 = new Student("رضا", "هدایتی" ,user1);
        studentRepository.save(student1);

        // student 2
        User user2 = new User("ahmad", bCryptPasswordEncoder.encode("1"), rolesStudent);
        user2.setEnabled(true);
        Student student2 = new Student("احمد", "حسینی", user2);
        studentRepository.save(student2);

        // student 3
        User user3 = new User("naghi", bCryptPasswordEncoder.encode("1"), rolesStudent);
        user3.setEnabled(false);
        Student student3 = new Student("نقی", "معمولی", user3);
        studentRepository.save(student3);

        Collection<Role> rolesTeacher = new HashSet<>();
        rolesTeacher.add(roleTeacher);

        // teacher 1
        User user5 = new User("ali", bCryptPasswordEncoder.encode("1"), rolesTeacher);
        user5.setEnabled(true);
        Teacher teacher1 = new Teacher("علی", "واحدی", user5);
        teacherRepository.save(teacher1);

        // teacher 2
        User user6 = new User("homa", bCryptPasswordEncoder.encode("1"), rolesTeacher);
        user6.setEnabled(true);
        Teacher teacher2 = new Teacher("هما", "سعادت", user6);
        teacherRepository.save(teacher2);

        // teacher 3
        User user7 = new User("mina", bCryptPasswordEncoder.encode("1"), rolesTeacher);
        user7.setEnabled(false);
        Teacher teacher3 = new Teacher("مینا", "ساداتی", user7);
        teacherRepository.save(teacher3);



        Date[] startDate = new Date[4];
        Date[] endDate = new Date[4];
        try {
            startDate[0] = new SimpleDateFormat("yyyy/MM/dd").parse("1398/11/15");
            endDate[0] = new SimpleDateFormat("yyyy/MM/dd").parse("1398/12/15");
            startDate[1] = new SimpleDateFormat("yyyy/MM/dd").parse("1399/11/15");
            endDate[1] = new SimpleDateFormat("yyyy/MM/dd").parse("1399/12/15");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Course course1 = new Course("جاوا", 1L,
                startDate[0],
                endDate[0]);
        course1.setTeacher(teacher1);
        Set<Student> students1 = new HashSet<>();
        students1.add(student1);
        course1.setStudents(students1);
        courseRepository.save(course1);


//        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//
//        String dateString = format.format( new Date()   );

        Course course2 = new Course("PHP", 2L,
                startDate[1],
                endDate[1]);
        course2.setTeacher(teacher1);
        Set<Student> students2 = new HashSet<>();
        students2.add(student1);
        students2.add(student2);
        course2.setStudents(students2);
        courseRepository.save(course2);

    }
}


