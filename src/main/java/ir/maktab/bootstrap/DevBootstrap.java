package ir.maktab.bootstrap;

import ir.maktab.model.course.Course;
import ir.maktab.model.role.Role;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.account.Account;
import ir.maktab.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
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
    private AccountRepository AccountRepository;

    @Autowired
    public DevBootstrap(StudentRepository studentRepository, TeacherRepository teacherRepository, RoleRepository roleRepository, CourseRepository courseRepository, AccountRepository AccountRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
        this.AccountRepository = AccountRepository;
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
        Account admin = new Account("admin", bCryptPasswordEncoder.encode("1"), rolesAdmin);
        admin.setEnabled(true);
        AccountRepository.save(admin);

        // student 1
        Account account1 = new Account("reza", bCryptPasswordEncoder.encode("1"), rolesStudent);
        account1.setEnabled(true);
        Student student1 = new Student("رضا", "هدایتی" , account1);
        studentRepository.save(student1);

        // student 2
        Account account2 = new Account("ahmad", bCryptPasswordEncoder.encode("1"), rolesStudent);
        account2.setEnabled(true);
        Student student2 = new Student("احمد", "حسینی", account2);
        studentRepository.save(student2);

        // student 3
        Account account3 = new Account("naghi", bCryptPasswordEncoder.encode("1"), rolesStudent);
        account3.setEnabled(false);
        Student student3 = new Student("نقی", "معمولی", account3);
        studentRepository.save(student3);

        Collection<Role> rolesTeacher = new HashSet<>();
        rolesTeacher.add(roleTeacher);

        // teacher 1
        Account account5 = new Account("ali", bCryptPasswordEncoder.encode("1"), rolesTeacher);
        account5.setEnabled(true);
        Teacher teacher1 = new Teacher("علی", "واحدی", account5);
        teacherRepository.save(teacher1);

        // teacher 2
        Account account6 = new Account("homa", bCryptPasswordEncoder.encode("1"), rolesTeacher);
        account6.setEnabled(true);
        Teacher teacher2 = new Teacher("هما", "سعادت", account6);
        teacherRepository.save(teacher2);

        // teacher 3
        Account account7 = new Account("mina", bCryptPasswordEncoder.encode("1"), rolesTeacher);
        account7.setEnabled(false);
        Teacher teacher3 = new Teacher("مینا", "ساداتی", account7);
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


