package dream.development.configuration;

import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    Line 19, 30, 35, need to get enable access to the H2 database console under Spring Security

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        Allow all requests to the root url and other pages (“/”,"/products","/product/show/*")
        httpSecurity.authorizeRequests().antMatchers("/", "/products", "/product/show/*").permitAll()
//        Allow all requests to the H2 database console url (“/console/*”). Comment to remove allow.
                .and().authorizeRequests().antMatchers("/console/**","/h2-console/**").permitAll()
//        Secures all other paths of the application to require authentication
                .anyRequest().authenticated()
                .and()
//        Allows everyone to view a custom /login page specified by loginPage()
                .formLogin().loginPage("/login").permitAll()
                .and()
//        Permits all to make logout calls
                .logout().permitAll();

//        Disable CSRF protection
        httpSecurity.csrf().disable();
//        Disable X-Frame-Options in Spring Security.
//        Disallows newer browsers to do some security checks and prevent clickjacking attacks.
//        Clickjacking is a malicious technique of tricking a Web user
//        into clicking on something different from what the user perceives they are clicking on
        httpSecurity.headers().frameOptions().disable();
    }

//    configures in-memory authentication with two users
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password("admin").roles("ADMIN")
//                .and().withUser("user").password("user").roles("USER");
//        ;
//    }

    private AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("daoAuthenticationProvider")
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(StrongPasswordEncryptor passwordEncryptor){
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        passwordEncoder.setPasswordEncryptor(passwordEncryptor);
        return passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                               UserDetailsService userDetailsService){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Autowired
    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

}
