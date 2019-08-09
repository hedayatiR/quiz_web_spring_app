package ir.maktab.security.service;

import ir.maktab.model.account.Account;
import ir.maktab.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MyUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    AccountRepository AccountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = AccountRepository.findByUsername(username);
        if (account == null) {
            System.out.println("UsernameNotFoundException thrown");
            throw new UsernameNotFoundException(username);
        }

//        if (account.getStatus() == UserStatusEnum.INACTIVATED) {
//            System.out.println("DisabledException thrown");
//            throw new DisabledException("hesab faaal niiii");
//        }

        logger.info("Account " + username + " is found." );
        return account;
    }

}
