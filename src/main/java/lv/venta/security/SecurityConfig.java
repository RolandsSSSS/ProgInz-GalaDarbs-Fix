package lv.venta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/course/showAll").hasAnyRole("admin", "USER")
                .requestMatchers("/course/showAll/**").hasAnyRole("admin", "USER")
                .requestMatchers("/course/addNew").hasRole("admin")
                .requestMatchers("/course/update/**").hasRole("admin")
                .requestMatchers("/course/remove/**").hasRole("admin")
                .requestMatchers("/course/Export").hasRole("admin")
                .requestMatchers("/course/Import").hasRole("admin")

                .requestMatchers("/Person/All").hasAnyRole("admin", "USER")
                .requestMatchers("/Person/All/**").hasAnyRole("admin", "USER")
                .requestMatchers("/Person/AddPage").hasRole("admin")
                .requestMatchers("http://localhost:8080/Person/AddPage").hasRole("admin")
                .requestMatchers("/Person/update/**").hasRole("admin")
                .requestMatchers("/Person/remove/**").hasRole("admin")
                .requestMatchers("/Person/Export").hasRole("admin")
                .requestMatchers("/Person/Import").hasRole("admin")

                .requestMatchers("/academicPersonel/showAll").hasAnyRole("admin", "USER")
                .requestMatchers("/academicPersonel/showAll/**").hasAnyRole("admin", "USER")
                .requestMatchers("/academicPersonel/addNew").hasRole("admin")
                .requestMatchers("/academicPersonel/update/**").hasRole("admin")
                .requestMatchers("/academicPersonel/remove/**").hasRole("admin")
                .requestMatchers("/academicPersonel/Export").hasRole("admin")
                .requestMatchers("/academicPersonel/Import").hasRole("admin")

                .requestMatchers("/thesis/showAll").hasAnyRole("admin", "USER")
                .requestMatchers("/thesis/showAll/**").hasAnyRole("admin", "USER")
                .requestMatchers("/thesis/addNew").hasRole("admin")
                .requestMatchers("/thesis/update/**").hasRole("admin")
                .requestMatchers("/thesis/remove/**").hasRole("admin")

                .requestMatchers("/User/showAll").hasAnyRole("admin", "USER")
                .requestMatchers("/User/showAll/**").hasAnyRole("admin", "USER")
                .requestMatchers("/User/addNew").hasRole("admin")
                .requestMatchers("/User/update/**").hasRole("admin")
                .requestMatchers("/User/remove/**").hasRole("admin")

                .requestMatchers("/comment/showAll").hasAnyRole("admin", "USER")
                .requestMatchers("/comment/showAll/**").hasAnyRole("admin", "USER")
                .requestMatchers("/comment/addNew").hasRole("admin")
                .requestMatchers("/comment/update/**").hasRole("admin")
                .requestMatchers("/comment/remove/**").hasRole("admin")

                .requestMatchers("/Student/All").hasAnyRole("admin", "USER")
                .requestMatchers("/Student/All/**").hasAnyRole("admin", "USER")
                .requestMatchers("/Student/AddPage").hasRole("admin")
                .requestMatchers("/Student/update/**").hasRole("admin")
                .requestMatchers("/Student/remove/**").hasRole("admin")

                .requestMatchers("/h2-console").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/profile").permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/my-access-denied");
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user1").password("{noop}password1").roles("USER")
                .and()
                .withUser("user2").password("{noop}password2").roles("USER")
                .and()
                .withUser("admin").password("{noop}adminpassword").roles("admin");
    }
}