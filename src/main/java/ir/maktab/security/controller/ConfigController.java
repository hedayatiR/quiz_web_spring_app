package ir.maktab.security.controller;

import ir.maktab.model.role.dto.RoleDto;
import ir.maktab.model.account.Account;
import ir.maktab.security.controller.dto.ConfigDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@CrossOrigin(origins = "*")
@RestController
public class ConfigController {

    @GetMapping(value = "/api/config")
    public ConfigDto whoami(HttpServletRequest request, HttpServletResponse response) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal == null || principal instanceof String) {
            return new ConfigDto("guest", new RoleDto("GUEST"));
        }

        Account account = (Account) principal;
        ConfigDto configDTO =
                new ConfigDto(account.getUsername(), new RoleDto(account.getRole().getName()));

//        configDTO.setRoles(account.getRoles().stream().map(r -> new RoleDto(r.getName())).collect(Collectors.toSet()));


        // set cookie again if client removes account cookie handy
        boolean cookieExisted = false;
        for (Cookie cookie :
                request.getCookies()) {
            if (cookie.getName().equals("account")) {
                cookieExisted = true;
                cookie.setValue(account.getUsername());
                response.addCookie(cookie);
            }
        }

        if (!cookieExisted){
            Cookie cookie = new Cookie("account", account.getUsername());
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return configDTO;
    }
}
