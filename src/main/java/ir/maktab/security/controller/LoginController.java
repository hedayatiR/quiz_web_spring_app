package ir.maktab.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.Iterator;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

//    @GetMapping("/login")
//    public String login() {
//        System.out.println("login empty");
//        return "login";
//    }

    @RequestMapping("/loginNeeded")
    public ResponseEntity loginManager() {
        System.out.println("loginNeeded");
        logger.debug("login Needed");
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


    @RequestMapping(value = "/login/failed")
    public ResponseEntity loginFailed() {
        System.out.println("login Failed !!!!!!!!!!!!!!!!!!!!!!");
        logger.debug("login Failed !!");

        return ResponseEntity.status(UNAUTHORIZED).build();
    }

    @RequestMapping(value = "/login/succeed")
    public ResponseEntity loginSucceed(HttpServletResponse response) {
        System.out.println("login Succeed !!!!!!!!!!!!!!!!!!!!!!");
        logger.debug("login Succeed !!");
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


}
