package ir.maktab.service.teacher.impl;

import ir.maktab.model.role.Role;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.user.UserStatusEnum;
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
    public Teacher update(Teacher teacher) {

        if( !(teacher.getUser().getPassword().equals(teacher.getUser().getRepeatPassword())) )
            throw new PasswordMismatchException("Passwords Mismatch");

        if (teacher.getUser().getPassword().isEmpty()){
            String oldPassword = repository.findById(teacher.getId()) .get().getUser().getPassword();
            teacher.getUser().setPassword(oldPassword);
            teacher.getUser().setRepeatPassword(oldPassword);
        }

        return super.update(teacher);
    }


    @Override
    public Teacher changeStatus(Long id){
        // some validation
        Optional<Teacher> teacher = repository.findById(id);
        teacher.get().getUser().setEnabled( !(teacher.get().getUser().isEnabled()) );
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
        return repository.findByUserEnabled(true);
    }

}
