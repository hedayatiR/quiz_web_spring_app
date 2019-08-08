package ir.maktab.security.service;

import ir.maktab.model.user.User;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MyUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("UsernameNotFoundException thrown");
            throw new UsernameNotFoundException(username);
        }

//        if (user.getStatus() == UserStatusEnum.INACTIVATED) {
//            System.out.println("DisabledException thrown");
//            throw new DisabledException("hesab faaal niiii");
//        }

        logger.info("User " + username + " is found." );
        return user;
    }

}
