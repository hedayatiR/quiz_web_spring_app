package ir.maktab.security.controller;

import ir.maktab.model.role.dto.RoleDto;
import ir.maktab.model.account.Account;
import ir.maktab.security.controller.dto.ConfigDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@RestController
public class ConfigController {

    @RequestMapping(value = "/api/config")
    public ConfigDto whoami(HttpServletRequest request, HttpServletResponse response) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal == null || principal instanceof String) {
            return new ConfigDto("guest");
        }

        Account account = (Account) principal;
        ConfigDto configDTO =
                new ConfigDto(account.getUsername());

        configDTO.setRoles(account.getRoles().stream().map(r -> new RoleDto(r.getName())).collect(Collectors.toSet()));

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
            response.addCookie(cookie);
        }

        return configDTO;
    }
}
