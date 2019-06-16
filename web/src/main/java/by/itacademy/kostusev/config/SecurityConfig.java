package by.itacademy.kostusev.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static by.itacademy.kostusev.entity.Role.ADMIN;
import static by.itacademy.kostusev.path.UrlPath.FORBIDDEN;
import static by.itacademy.kostusev.path.UrlPath.PRODUCT_URL;
import static by.itacademy.kostusev.path.UrlPath.SIGNIN_URL;
import static by.itacademy.kostusev.path.UrlPath.USER_URL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ADMIN_PAGES = {
            USER_URL
    };

    private final UserDetailsService userDetailsService;

    // @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(ADMIN_PAGES)
                        .hasAuthority(ADMIN.toString())
                    .anyRequest()
                        .permitAll()
                .and()
                    .formLogin()
                    .loginPage(SIGNIN_URL)
                    .defaultSuccessUrl(PRODUCT_URL)
                .and()
                    .httpBasic()
                .and()
                    .logout()
                    .logoutSuccessUrl(PRODUCT_URL)
                .and()
                    .exceptionHandling().accessDeniedPage(FORBIDDEN)
                .and()
                    .userDetailsService(userDetailsService);
    }
    // @formatter:on

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}
