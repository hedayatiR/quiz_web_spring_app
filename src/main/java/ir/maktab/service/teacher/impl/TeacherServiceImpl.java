package ir.maktab.service.teacher.impl;

import ir.maktab.model.role.Role;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.repository.RoleRepository;
import ir.maktab.repository.TeacherRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.student.exception.PasswordMismatchException;
import ir.maktab.service.teacher.TeacherService;
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

public class TeacherServiceImpl extends BaseServiceImpl<Teacher, Long, TeacherRepository>
        implements TeacherService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public TeacherServiceImpl(TeacherRepository repository) {
        super(repository);
    }


    @Override
    public Teacher save(Teacher t) {

        Collection<Role> roles = new HashSet<>();
        for (Role roleIteration :
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
    public Teacher update(Teacher teacher) {

        if( !(teacher.getAccount().getPassword().equals(teacher.getAccount().getRepeatPassword())) )
            throw new PasswordMismatchException("Passwords Mismatch");

        if (teacher.getAccount().getPassword().isEmpty()){
            String oldPassword = repository.findById(teacher.getId()) .get().getAccount().getPassword();
            teacher.getAccount().setPassword(oldPassword);
            teacher.getAccount().setRepeatPassword(oldPassword);
        }

        return super.update(teacher);
    }


    @Override
    public Teacher changeStatus(Long id){
        // some validation
        Optional<Teacher> teacher = repository.findById(id);
        teacher.get().getAccount().setEnabled( !(teacher.get().getAccount().isEnabled()) );
        return super.save(teacher.get());
    }


    @Override
    @Transactional(readOnly = true)
    public Set<Teacher> findByCourseId(Long id) {
        return repository.findByCoursesId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Teacher> findActivatedTeachers() {
        return repository.findByAccountEnabled(true);
    }

}
