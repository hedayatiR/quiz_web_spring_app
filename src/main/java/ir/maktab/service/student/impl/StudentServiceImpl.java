package ir.maktab.service.student.impl;

import ir.maktab.model.role.Role;
import ir.maktab.model.student.Student;
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

        if ( !(t.getAccount().getPassword().equals(t.getAccount().getRepeatPassword())) ){
            System.out.println("PasswordMismatchException thrown");
            throw new PasswordMismatchException("Passwords mismatch shode!") ;
        }


        Collection<Role> roles = new HashSet<>();
        for (Role roleIteration:
             t.getAccount().getRoles()) {
            Role role = roleRepository.findByName(roleIteration.getName());
            roles.add(role);
        }
        t.getAccount().setRoles(roles);

        // encrypt password
        t.getAccount().setPassword(bCryptPasswordEncoder.encode(t.getAccount().getPassword()));

        return super.save(t);
    }


    @Override
    public Student update(Student student) {

        if( !(student.getAccount().getPassword().equals(student.getAccount().getRepeatPassword())) )
            throw new PasswordMismatchException("Passwords Mismatch");

        if (student.getAccount().getPassword().isEmpty()){
            String oldPassword = repository.findById(student.getId()) .get().getAccount().getPassword();
            student.getAccount().setPassword(oldPassword);
            student.getAccount().setRepeatPassword(oldPassword);
        }


        return super.update(student);
    }

    @Override
    public Student changeStatus(Long id){
        // some validation
//        student.getUser().setStatus(status);
        Optional<Student> student = repository.findById(id);
        student.get().getAccount().setEnabled( !(student.get().getAccount().isEnabled()) );

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
        return repository.findByAccountEnabled(true);
    }


}
