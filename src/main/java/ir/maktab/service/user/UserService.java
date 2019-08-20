package ir.maktab.service.user;

import ir.maktab.mapper.UserMapper;
import ir.maktab.model.user.User;
import ir.maktab.model.user.dto.UserDto;
import ir.maktab.service.base.BaseService;

import java.util.Collection;

public interface UserService extends BaseService<User, UserDto, Long, UserMapper> {

    UserDto changeStatus(Long id);

    Collection<UserDto> findAllStudentsByCourseId(Long id);

    Collection<UserDto> findActivatedUsersByRole(String role);
    Collection<UserDto> findAllActivatedUsers();
}
