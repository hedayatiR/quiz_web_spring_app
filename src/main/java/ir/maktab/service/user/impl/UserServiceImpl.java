package ir.maktab.service.user.impl;

import ir.maktab.mapper.RoleMapper;
import ir.maktab.mapper.UserMapper;
import ir.maktab.model.role.Role;
import ir.maktab.model.user.User;
import ir.maktab.model.user.dto.UserDto;
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

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserDto, Long, UserRepository, UserMapper>
        implements UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository repository, UserMapper baseMapper) {
        super(repository, baseMapper);
    }


    @Override
    public UserDto save(UserDto userDto) {

        User user = baseMapper.dtoToEntity(userDto, User.class);

        if (!(user.getAccount().getPassword().equals(user.getAccount().getRepeatPassword()))) {
            System.out.println("PasswordMismatchException thrown");
            throw new PasswordMismatchException("Passwords mismatch shode!");
        }

        Role role = roleRepository.findByName(user.getAccount().getRole().getName());
        user.getAccount().setRole(role);

        // encrypt password
        user.getAccount().setPassword(bCryptPasswordEncoder.encode(userDto.getAccount().getPassword()));
        User savedUser = repository.save(user);
        return baseMapper.entityToDto(savedUser, UserDto.class);
    }


    @Override
    public UserDto update(UserDto userdto) {

        User user = baseMapper.dtoToEntity(userdto, User.class);

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

        User savedUser = repository.save(user);
        return baseMapper.entityToDto(savedUser, UserDto.class);
    }

    @Override
    public UserDto changeStatus(Long id) {
        // some validation
//        user.getUser().setStatus(status);
        Optional<User> user = repository.findById(id);
        user.get().getAccount().setEnabled(!(user.get().getAccount().isEnabled()));

        User savedUser = repository.save(user.get());
        return baseMapper.entityToDto(savedUser, UserDto.class);
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<UserDto> findAllStudentsByCourseId(Long id) {
        Set<User> users = repository.findByStudentCoursesId(id);
        return baseMapper.entityToDtoCollection(users, UserDto.class);
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<UserDto> findActivatedUsersByRole(String role) {
        Set<User> users = repository.findByAccount_EnabledAndAccount_Role_Name(true, role);
        return baseMapper.entityToDtoCollection(users, UserDto.class);
    }

    @Override
    public Collection<UserDto> findAllActivatedUsers() {
        Set<User> users = repository.findByAccount_Enabled(true);
        return baseMapper.entityToDtoCollection(users, UserDto.class);
    }


    }
