package esdp.crm.attractor.school.configuration;

import lombok.RequiredArgsConstructor;
import org.javers.spring.auditable.AuthorProvider;
import org.javers.spring.auditable.SpringSecurityAuthorProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthorProvider authorProvider() {
        return new SpringSecurityAuthorProvider();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/operations/**").fullyAuthenticated()
                .antMatchers("/tasks/**").fullyAuthenticated()
                .antMatchers("/application/**").fullyAuthenticated()
                .antMatchers("/").fullyAuthenticated()
                .anyRequest().permitAll();

        http.formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/users/login")
                .defaultSuccessUrl("/");
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .invalidateHttpSession(true);

        http.csrf();
        http.cors().disable();
    }
}
