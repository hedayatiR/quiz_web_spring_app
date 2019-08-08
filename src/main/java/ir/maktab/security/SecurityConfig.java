package ir.maktab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private String[] freeResources = new String[]{
            "/", "/**.html", "/assets/**", "/h2-console/**"
    };

    // dont put two common url in different access
    // for this purpose define a new fiter chain(antmatchers)
    private String[] SIGNUP_PERMIT = new String[]{
            "/api/students", "/api/teachers"
    };

    private String[] STUDENT_ACCESS = new String[]{
            "/api/students/**"
    };

    private String[] TEACHER_ACCESS = new String[]{
            "/api/teachers/**"
    };

    private String[] ADMIN_ACCESS = new String[]{
            "/api/**"
    };


    @Autowired
    @Qualifier(value = "MyUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    private MyAuthExceptionHandlerEntryPoint myAuthExceptionHandlerEntryPoint;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(freeResources);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGNUP_PERMIT).permitAll()
//                .antMatchers(STUDENT_ACCESS).hasAuthority("STUDENT")
//                .antMatchers(TEACHER_ACCESS).hasAuthority("TEACHER")
                .antMatchers("/api/config").hasAnyAuthority("TEACHER", "STUDENT", "ADMIN")
                .antMatchers("/api/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling().authenticationEntryPoint(myAuthExceptionHandlerEntryPoint)
                .and()
                .formLogin()
                .loginPage("/loginNeeded").permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureForwardUrl("/login/failed")
                .successForwardUrl("/login/succeed")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID", "user")
        ;
    }


}
