package am.itspace.bigbag.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Qualifier(value = "userDetailServiceImpl")
    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/signIn")
                .loginProcessingUrl("/signIn")
//                .successForwardUrl("/")
                .defaultSuccessUrl("/successLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/signIn").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/adminProfile").hasAnyAuthority("ADMIN")
                .antMatchers("/user").hasAnyAuthority("USER")
                .antMatchers("/allUsers").hasAnyAuthority("ADMIN")
                .antMatchers("/allProducts").hasAnyAuthority("ADMIN");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
