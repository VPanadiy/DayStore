package dream.development.configuration;

import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //    password encoder reference implemented in WebMvcConfiguration.java
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //    data source implemented out of the box by Spring Boot.
    // We only need to provide the database information in the application.properties file
//    @Autowired
//    private DataSource dataSource;

    //    Reference to user queries stored in application.properties file
//    @Value("${spring.queries.users-query}")
//    private String usersQuery;

    //    Reference to role queries stored in application.properties file
//    @Value("${spring.queries.roles-query}")
//    private String rolesQuery;

    //    AuthenticationManagerBuilder provides a mechanism to get a user based on the password encoder, data source, user query and role query.
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
//                .usersByUsernameQuery(usersQuery)
//                .authoritiesByUsernameQuery(rolesQuery)
//                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    //    CAUTION: DO NOT USE IN PRODUCTION
//    Line 69, 87, 92, need to get enable access to the H2 database console under Spring Security
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        Allow all requests to the root url and other pages (“/”,"/products","/product/show/*")
        httpSecurity
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/products").permitAll()
                .antMatchers("/product/show/*").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
//        Allow all requests to the H2 database console url (“/console/*”). Comment to remove allow.
                .and().authorizeRequests().antMatchers("/console/**", "/h2-console/**").permitAll()
//        Admin role
                .and().authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN")
//        Secures all other paths of the application to require authentication
                .anyRequest().authenticated()
                .and()
//        Allows everyone to view a custom /login page specified by loginPage()
                .formLogin().loginPage("/login").failureUrl("/login?error=true").permitAll()
                .defaultSuccessUrl("/admin/home")
                .usernameParameter("email")
                .passwordParameter("password")
//        Permits all to make logout calls
                .and().logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");

//        Disable CSRF protection
        httpSecurity.csrf().disable();
//        Disable X-Frame-Options in Spring Security.
//        Disallows newer browsers to do some security checks and prevent clickjacking attacks.
//        Clickjacking is a malicious technique of tricking a Web user
//        into clicking on something different from what the user perceives they are clicking on
        httpSecurity.headers().frameOptions().disable();
    }

    //    let Spring knows that our resources folder can be served skipping the antMatchers defined
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
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
}
