package ir.maktab.bootstrap;

import ir.maktab.model.course.Course;
import ir.maktab.model.role.Role;
import ir.maktab.model.user.User;
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


    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CourseRepository courseRepository;
    private AccountRepository accountRepository;

    @Autowired
    public DevBootstrap(UserRepository userRepository, RoleRepository roleRepository, CourseRepository courseRepository, ir.maktab.repository.AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
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


        // admin
        Account admin = new Account("admin", bCryptPasswordEncoder.encode("1"), roleAdmin);
        admin.setEnabled(true);
        accountRepository.save(admin);

        // student 1
        Account account1 = new Account("reza", bCryptPasswordEncoder.encode("1"), roleStudent);
        account1.setEnabled(true);

        User student1 = new User("رضا", "هدایتی", account1);
        userRepository.save(student1);


        // student 2
        Account account2 = new Account("ahmad", bCryptPasswordEncoder.encode("1"), roleStudent);
        account1.setEnabled(true);

        User student2 = new User("احمد", "حسینی", account2);
        userRepository.save(student2);


        // student 3
        Account account3 = new Account("naghi", bCryptPasswordEncoder.encode("1"), roleStudent);
        account1.setEnabled(false);
        User student3 = new User("نقی", "معمولی", account3);
        userRepository.save(student3);


        // teacher 1
        Account account5 = new Account("ali", bCryptPasswordEncoder.encode("1"), roleTeacher);
        account5.setEnabled(true);
        User teacher1 = new User("علی", "واحدی", account5);
        userRepository.save(teacher1);

        // teacher 2
        Account account6 = new Account("homa", bCryptPasswordEncoder.encode("1"), roleTeacher);
        account6.setEnabled(true);
        User teacher2 = new User("هما", "سعادت", account6);
        userRepository.save(teacher2);

        // teacher 3
        Account account7 = new Account("mina", bCryptPasswordEncoder.encode("1"), roleTeacher);
        account7.setEnabled(false);
        User teacher3 = new User("مینا", "ساداتی", account7);
        userRepository.save(teacher3);


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


        Course course1 = new Course("جاوا", startDate[0], endDate[0]);

        course1.setTeacher(teacher1);
        Set<User> students1 = new HashSet<>();
        students1.add(student1);
        course1.setStudents(students1);
        courseRepository.save(course1);


//        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//
//        String dateString = format.format( new Date()   );

        Course course2 = new Course("PHP", startDate[1], endDate[1]);
        course2.setTeacher(teacher1);
        Set<User> students2 = new HashSet<>();
        students2.add(student1);
        students2.add(student2);
        course2.setStudents(students2);
        courseRepository.save(course2);

    }
}


