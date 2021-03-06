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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import static by.itacademy.kostusev.entity.Role.ADMIN;
import static by.itacademy.kostusev.entity.Role.CUSTOMER;
import static by.itacademy.kostusev.path.UrlPath.ADMIN_ORDERS_URL;
import static by.itacademy.kostusev.path.UrlPath.ADMIN_PRODUCTS_URL;
import static by.itacademy.kostusev.path.UrlPath.CUSTOMER_ACCOUNT_URL;
import static by.itacademy.kostusev.path.UrlPath.FORBIDDEN;
import static by.itacademy.kostusev.path.UrlPath.PRODUCT_URL;
import static by.itacademy.kostusev.path.UrlPath.SIGNIN_URL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ADMIN_PAGES = {
            ADMIN_ORDERS_URL, ADMIN_PRODUCTS_URL
    };

    private static final String[] AUTHORIZED_USER_PAGES = {
            CUSTOMER_ACCOUNT_URL
    };

    private static final String[] ANY_USER_PAGES = {
            PRODUCT_URL
    };

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(encodingFilter(), CsrfFilter.class);
        // @formatter:off
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers(ADMIN_PAGES)
                        .hasAuthority(ADMIN.toString())
                    .antMatchers(AUTHORIZED_USER_PAGES)
                        .hasAuthority(CUSTOMER.toString())
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
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling().accessDeniedPage(FORBIDDEN)
                .and()
                    .userDetailsService(userDetailsService);
          // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
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
