package ir.maktab.bootstrap;

import ir.maktab.model.course.Course;
import ir.maktab.model.exam.Exam;
import ir.maktab.model.question.BaseQuestion;
import ir.maktab.model.question.DescriptiveQuestion;
import ir.maktab.model.question.TestQuestion;
import ir.maktab.model.question.option.Option;
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
    private ExamRepository examRepository;
    private BaseQuestionRepository baseQuestionRepository;

    @Autowired
    public DevBootstrap(UserRepository userRepository, RoleRepository roleRepository, CourseRepository courseRepository, AccountRepository accountRepository, ExamRepository examRepository, BaseQuestionRepository baseQuestionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
        this.accountRepository = accountRepository;
        this.examRepository = examRepository;
        this.baseQuestionRepository = baseQuestionRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }


    private void initData() {

        Role roleStudent = new Role("STUDENT");
        roleStudent = roleRepository.save(roleStudent);
        Role roleTeacher = new Role("TEACHER");
        roleTeacher = roleRepository.save(roleTeacher);
        Role roleAdmin = new Role("ADMIN");
        roleAdmin = roleRepository.save(roleAdmin);


        // admin
        Account admin = new Account("admin", bCryptPasswordEncoder.encode("1"), roleAdmin);
        admin.setEnabled(true);
        admin = accountRepository.save(admin);

        // student 1
        Account account1 = new Account("reza", bCryptPasswordEncoder.encode("1"), roleStudent);
        account1.setEnabled(true);

        User student1 = new User("رضا", "هدایتی", account1);
        student1 = userRepository.save(student1);


        // student 2
        Account account2 = new Account("ahmad", bCryptPasswordEncoder.encode("1"), roleStudent);
        account1.setEnabled(true);

        User student2 = new User("احمد", "حسینی", account2);
        student2 = userRepository.save(student2);


        // student 3
        Account account3 = new Account("naghi", bCryptPasswordEncoder.encode("1"), roleStudent);
        account1.setEnabled(false);
        User student3 = new User("نقی", "معمولی", account3);
        student3 = userRepository.save(student3);


        // teacher 1
        Account account5 = new Account("ali", bCryptPasswordEncoder.encode("1"), roleTeacher);
        account5.setEnabled(true);
        User teacher1 = new User("علی", "واحدی", account5);
        teacher1 = userRepository.save(teacher1);

        // teacher 2
        Account account6 = new Account("homa", bCryptPasswordEncoder.encode("1"), roleTeacher);
        account6.setEnabled(true);
        User teacher2 = new User("هما", "سعادت", account6);
        teacher2 = userRepository.save(teacher2);

        // teacher 3
        Account account7 = new Account("mina", bCryptPasswordEncoder.encode("1"), roleTeacher);
        account7.setEnabled(false);
        User teacher3 = new User("مینا", "ساداتی", account7);
        teacher3 = userRepository.save(teacher3);


        // Generate Dates
        Date[] startDate = new Date[4];
        Date[] endDate = new Date[4];
        Date[] examDates = new Date[4];
        try {
            startDate[0] = new SimpleDateFormat("yyyy/MM/dd").parse("1398/11/15");
            endDate[0] = new SimpleDateFormat("yyyy/MM/dd").parse("1398/12/15");
            startDate[1] = new SimpleDateFormat("yyyy/MM/dd").parse("1399/11/15");
            endDate[1] = new SimpleDateFormat("yyyy/MM/dd").parse("1399/12/15");
            examDates[0] = new SimpleDateFormat("yyyy/MM/dd").parse("1397/12/15");
            examDates[1] = new SimpleDateFormat("yyyy/MM/dd").parse("1396/12/15");
            examDates[2] = new SimpleDateFormat("yyyy/MM/dd").parse("1399/12/20");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        // course1
        Course javaCourse = new Course("جاوا", startDate[0], endDate[0]);

        javaCourse.setTeacher(teacher1);
        Set<User> students1 = new HashSet<>();
        students1.add(student1);
        javaCourse.setStudents(students1);
        javaCourse = courseRepository.save(javaCourse);


//        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//
//        String dateString = format.format( new Date()   );

        // course 2
        Course phpCourse = new Course("PHP", startDate[1], endDate[1]);
        phpCourse.setTeacher(teacher2);
        Set<User> students2 = new HashSet<>();
        students2.add(student1);
        students2.add(student2);
        phpCourse.setStudents(students2);
        phpCourse = courseRepository.save(phpCourse);

        // exam 1
        Exam javaExam1 = new Exam("Java Exam 1", "Exceptions", 10, examDates[0]);
        javaExam1.setCourse(javaCourse);
        javaExam1 = examRepository.save(javaExam1);

        // exam 2
        Exam javaExam2 = new Exam("Java Exam 2", "Spring", 20, examDates[1]);
        javaExam2.setCourse(javaCourse);
        javaExam2 = examRepository.save(javaExam2);

        // exam 3
        Exam phpExam1 = new Exam("PHP Exam 1", "php intro", 5, examDates[2]);
        phpExam1.setCourse(phpCourse);
        phpExam1 = examRepository.save(phpExam1);

        // make questions for exam 1
        // question 1 - test 1
        Set<Option> options1 = new HashSet<>();
        options1.add(new Option("gozine 1"));
        options1.add(new Option("gozine 2"));
        options1.add(new Option("gozine 3"));
        options1.add(new Option("gozine 4"));
        TestQuestion testQuestion1 = new TestQuestion("Java Exam 1 first test question");
        testQuestion1.setQuestionContent("Which one is right about Exception?");
        testQuestion1.setOptions(options1);
        testQuestion1.setRightChoice(1);
        testQuestion1.setCourse(javaCourse);

        javaExam1 = examRepository.findByIdFetchQuestions(javaExam1.getId());
        javaExam1.addQuestion(testQuestion1);
        javaExam1 = examRepository.save(javaExam1);

        // question 2 - desc 1
        DescriptiveQuestion descriptiveQuestion1 = new DescriptiveQuestion("Java first desc question");
        descriptiveQuestion1.setQuestionContent("Describe about Inheritance Strategy in JPA.");
        descriptiveQuestion1.setRightAnswer("4 strategy. I selected JOINED");
        descriptiveQuestion1.setCourse(javaCourse);

        javaExam1.addQuestion(descriptiveQuestion1);
        javaExam1 = examRepository.save(javaExam1);


        //
//        Exam javaNewExam = examRepository.findByIdFetchQuestions(javaExam1.getId());

        List<BaseQuestion> questions11 = baseQuestionRepository.findAll();


        for (BaseQuestion question :
//                javaNewExam.getQuestions()) {
                questions11) {
            if (question.getType().equals("TestQuestion")) {
                System.out.println(question.getQuestionContent());
                System.out.println(((TestQuestion) question).getOptions());
                System.out.println(((TestQuestion) question).getRightChoice());
            } else if (question.getType().equals("DescriptiveQuestion")) {
                System.out.println(question.getQuestionContent());
                System.out.println(((DescriptiveQuestion) question).getRightAnswer());
            }
        }


    }
}


