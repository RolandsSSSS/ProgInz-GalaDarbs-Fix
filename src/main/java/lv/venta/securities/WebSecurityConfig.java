package lv.venta.securities;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.DispatcherType;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
public class WebSecurityConfig {

/*
    protected void configure1(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .requestMatchers("/Student/All").access("hasRole('priviledged') and hasAuthority('ou=ADMIN,dc=springframework,dc=org')")
           //     .requestMatchers("/pages-restricted-to-otherpeople/**").access("hasRole('ROLE_USER') and hasAuthority('ou=otherpeople,ou=ADMIN,dc=springframework,dc=org')")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
            .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }
    */

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.ldapAuthentication()
				.userDnPatterns("uid={0},ou=TEACHER", "uid={0},ou=STUDENT","uid={0},ou=ADMIN")
				.groupSearchBase("ou=USER")
				.contextSource()
					.url("ldap://localhost:8389/dc=springframework,dc=org")
					.and()
				.passwordCompare()
					.passwordEncoder(new BCryptPasswordEncoder())
					.passwordAttribute("userPassword");
	}


	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.requestMatchers("/").permitAll()
		.requestMatchers("/course/showAll").permitAll()
		.requestMatchers("/course/showAll/**").permitAll()
		.requestMatchers("/course/addNew").fullyAuthenticated()
		.requestMatchers("/course/update/**").fullyAuthenticated()
		.requestMatchers("/course/remove/**").fullyAuthenticated()
		
		.requestMatchers("/Person/All").fullyAuthenticated()
		.requestMatchers("/Person/All/**").fullyAuthenticated()
		.requestMatchers("/Person/AddPage").fullyAuthenticated()
		.requestMatchers("/Person/update/**").fullyAuthenticated()
		.requestMatchers("/Person/remove/**").fullyAuthenticated()
		
		.requestMatchers("/academicPersonel/showAll").fullyAuthenticated()
		.requestMatchers("/academicPersonel/showAll/**").fullyAuthenticated()
		.requestMatchers("/academicPersonel/addNew").fullyAuthenticated()
		.requestMatchers("/academicPersonel/update/**").fullyAuthenticated()
		.requestMatchers("/academicPersonel/remove/**").fullyAuthenticated()
		
		.requestMatchers("/thesis/showAll").fullyAuthenticated()
		.requestMatchers("/thesis/showAll/**").fullyAuthenticated()
		.requestMatchers("/thesis/addNew").fullyAuthenticated()
		.requestMatchers("/thesis/update/**").fullyAuthenticated()
		.requestMatchers("/thesis/remove/**").fullyAuthenticated()
		
		.requestMatchers("/User/showAll").fullyAuthenticated()
		.requestMatchers("/User/showAll/**").fullyAuthenticated()
		.requestMatchers("/User/addNew").fullyAuthenticated()
		.requestMatchers("/User/update/**").fullyAuthenticated()
		.requestMatchers("/User/remove/**").fullyAuthenticated()
		
		.requestMatchers("/comment/showAll").fullyAuthenticated()
		.requestMatchers("/comment/showAll/**").fullyAuthenticated()
		.requestMatchers("/comment/addNew").fullyAuthenticated()
		.requestMatchers("/comment/update/**").fullyAuthenticated()
		.requestMatchers("/comment/remove/**").fullyAuthenticated()
		
		
	
		.requestMatchers("/Student/All").fullyAuthenticated()
		.requestMatchers("/Student/All/**").fullyAuthenticated()
		.requestMatchers("/Student/AddPage").fullyAuthenticated()
		.requestMatchers("/Student/update/**").fullyAuthenticated()
		.requestMatchers("/Student/remove/**").fullyAuthenticated()

    
		.requestMatchers("/h2-console").permitAll()
		.requestMatchers("/h2-console/**").permitAll()
		.and()
		.formLogin().permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/my-access-denied");
		return http.build();
	}
}
