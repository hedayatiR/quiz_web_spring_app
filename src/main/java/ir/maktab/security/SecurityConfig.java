package ir.maktab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
            "/api/students", "/api/users"
    };

    private String[] STUDENT_ACCESS = new String[]{
    };

    private String[] TEACHER_ACCESS = new String[]{
            "/api/courses/findByTeacherUsername/**",
            "/api/exams/**",
    };

    private String[] ADMIN_ACCESS = new String[]{
            "/api/**"
    };


//    @Autowired
//    @Qualifier(value = "MyUserDetailsService")
//    private UserDetailsService userDetailsService;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(freeResources);
    }


//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

    @Bean
    protected AuthenticationManager myAuthenticationManager() throws Exception {
        return authenticationManager();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGNUP_PERMIT).permitAll()

                .antMatchers("/api/questions/**").permitAll()

                .antMatchers("/login").permitAll()
                .antMatchers(STUDENT_ACCESS).hasAuthority("STUDENT")
                .antMatchers(TEACHER_ACCESS).hasAuthority("TEACHER")
                .antMatchers("/api/config").authenticated()
                .antMatchers(ADMIN_ACCESS).hasAuthority("ADMIN")
                .anyRequest().authenticated()

//                .anyRequest().permitAll()

                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID", "account")
        ;

    }

}
