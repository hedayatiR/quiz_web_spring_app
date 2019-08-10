package ir.maktab.repository;

import ir.maktab.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Set<User> findByStudentCoursesId(Long id);
    Set<User> findByAccount_EnabledAndAccount_Role_Name(boolean enabled, String role_name);
    Set<User> findByAccount_Enabled(boolean enabled);
}
