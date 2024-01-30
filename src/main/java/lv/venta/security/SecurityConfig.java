package lv.venta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        // General security configuration
        http
                .authorizeRequests()
                .requestMatchers("/public").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // User authentication configuration
        auth
                .inMemoryAuthentication()
                .withUser("user1").password("{noop}password1").roles("USER")
                .and()
                .withUser("user2").password("{noop}password2").roles("USER");
    }
}