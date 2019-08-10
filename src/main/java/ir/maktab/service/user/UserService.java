package ir.maktab.service.user;

import ir.maktab.model.user.User;
import ir.maktab.service.base.BaseService;

import java.util.Set;

public interface UserService extends BaseService<User, Long> {

    User changeStatus(Long id);

    Set<User> findAllStudentsByCourseId(Long id);

    Set<User> findActivatedUsersByRole(String role);
    Set<User> findAllActivatedUsers();
}
