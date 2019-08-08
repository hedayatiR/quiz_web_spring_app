package ir.maktab.service.student.impl;

import ir.maktab.model.course.Course;
import ir.maktab.model.role.Role;
import ir.maktab.model.student.Student;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.repository.RoleRepository;
import ir.maktab.repository.StudentRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.student.StudentService;
import ir.maktab.service.student.exception.PasswordMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentRepository>
    implements StudentService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public StudentServiceImpl(StudentRepository baseRepository) {
        super(baseRepository);
    }



    @Override
    public Student save(Student t) {

        if ( !(t.getUser().getPassword().equals(t.getUser().getRepeatPassword())) ){
            System.out.println("PasswordMismatchException thrown");
            throw new PasswordMismatchException("Passwords mismatch shode!") ;
        }


        Collection<Role> roles = new HashSet<>();
        for (Role roleIteration:
             t.getUser().getRoles()) {
            Role role = roleRepository.findByName(roleIteration.getName());
            roles.add(role);
        }
        t.getUser().setRoles(roles);

        // encrypt password
        t.getUser().setPassword(bCryptPasswordEncoder.encode(t.getUser().getPassword()));

        return super.save(t);
    }


    @Override
    public Student update(Student student) {

        if( !(student.getUser().getPassword().equals(student.getUser().getRepeatPassword())) )
            throw new PasswordMismatchException("Passwords Mismatch");

        if (student.getUser().getPassword().isEmpty()){
            String oldPassword = repository.findById(student.getId()) .get().getUser().getPassword();
            student.getUser().setPassword(oldPassword);
            student.getUser().setRepeatPassword(oldPassword);
        }


        return super.update(student);
    }

    @Override
    public Student changeStatus(Long id){
        // some validation
//        student.getUser().setStatus(status);
        Optional<Student> student = repository.findById(id);
        student.get().getUser().setEnabled( !(student.get().getUser().isEnabled()) );

        return super.save(student.get());
    }


    @Override
    @Transactional(readOnly = true)
    public Set<Student> findAllByCourseId(Long id) {
        return repository.findByCoursesId(id);
    }


    @Override
    @Transactional(readOnly = true)
    public Set<Student> findActivatedStudents() {
        return repository.findByUserEnabled(true);
    }


}
