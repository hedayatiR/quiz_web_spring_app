package ir.maktab.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Iterator;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = {"ROLES"})
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    @Qualifier(value = "MyUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/login")
    public ResponseEntity loginProcessing(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDetails userDetails = null;
        Authentication auth;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    password, userDetails.getAuthorities());

            auth = authenticationManager.authenticate(authenticationToken);

        } catch (DisabledException e) {
            if (bCryptPasswordEncoder.matches(password, userDetails.getPassword()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("hesabet fall nist");
            else
                return ResponseEntity.status(UNAUTHORIZED).body("username eshtebahe");

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(UNAUTHORIZED).body("password eshtebahe");

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(UNAUTHORIZED).body("username eshtebahe");
        }


        if (auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
            Cookie cookie = new Cookie("account", SecurityContextHolder.getContext().getAuthentication().getName());
            response.addCookie(cookie);
            logger.info("account cookie set : " + cookie.getValue());

            // set roles as header in response
            Iterator<? extends GrantedAuthority> iterator = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator();
            String roles = "";
            boolean hasFirst = iterator.hasNext();
            while (hasFirst) {
                roles += iterator.next().getAuthority();
                if (iterator.hasNext()) {
                    roles += ",";
                } else
                    break;
            }

            response.addHeader("ROLES", roles);

            return ResponseEntity.ok().build();
        }
        return null;
    }

}


