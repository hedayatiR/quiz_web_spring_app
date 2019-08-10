package ir.maktab.service.user.impl;

import ir.maktab.model.role.Role;
import ir.maktab.model.user.User;
import ir.maktab.repository.AccountRepository;
import ir.maktab.repository.RoleRepository;
import ir.maktab.repository.UserRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.user.UserService;
import ir.maktab.service.user.exception.PasswordMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository>
        implements UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository baseRepository) {
        super(baseRepository);
    }


    @Override
    public User save(User t) {

        if (!(t.getAccount().getPassword().equals(t.getAccount().getRepeatPassword()))) {
            System.out.println("PasswordMismatchException thrown");
            throw new PasswordMismatchException("Passwords mismatch shode!");
        }

        Role role = roleRepository.findByName(t.getAccount().getRole().getName());
        t.getAccount().setRole(role);

        // encrypt password
        t.getAccount().setPassword(bCryptPasswordEncoder.encode(t.getAccount().getPassword()));

        return super.save(t);
    }


    @Override
    public User update(User user) {

        if ((user.getAccount().getPassword() == null) ||
                (user.getAccount().getRepeatPassword() == null) ||
                (user.getAccount().getPassword().isEmpty()) ||
                (user.getAccount().getRepeatPassword().isEmpty())
        ) {
            String oldPassword = accountRepository.findById(user.getAccount().getId()).get().getPassword();
            user.getAccount().setPassword(oldPassword);
            user.getAccount().setRepeatPassword(oldPassword);

        } else if (!(user.getAccount().getPassword().equals(user.getAccount().getRepeatPassword())))
            throw new PasswordMismatchException("Passwords Mismatch");
        else {
            user.getAccount().setPassword(bCryptPasswordEncoder.encode(user.getAccount().getPassword()));
        }


        return super.save(user); // super.save must be called not update
    }

    @Override
    public User changeStatus(Long id) {
        // some validation
//        user.getUser().setStatus(status);
        Optional<User> user = repository.findById(id);
        user.get().getAccount().setEnabled(!(user.get().getAccount().isEnabled()));

        return super.save(user.get());
    }


    @Override
    @Transactional(readOnly = true)
    public Set<User> findAllStudentsByCourseId(Long id) {
        return repository.findByStudentCoursesId(id);
    }


    @Override
    @Transactional(readOnly = true)
    public Set<User> findActivatedUsersByRole(String role) {
        return repository.findByAccount_EnabledAndAccount_Role_Name(true, role);
    }

    @Override
    public Set<User> findAllActivatedUsers() {
        return repository.findByAccount_Enabled(true);
    }


}
