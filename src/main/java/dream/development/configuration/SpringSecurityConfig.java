package dream.development.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    Line 17, 20, 25, need to get enable access to the H2 database console under Spring Security

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        Allow all requests to the root url (“/”)
        httpSecurity.authorizeRequests().antMatchers("/").permitAll()
//        Allow all requests to the H2 database console url (“/console/*”)
                .and().authorizeRequests().antMatchers("/console/**").permitAll();

//        Disable CSRF protection
        httpSecurity.csrf().disable();
//        Disable X-Frame-Options in Spring Security.
//        Disallows newer browsers to do some security checks and prevent clickjacking attacks.
//        Clickjacking is a malicious technique of tricking a Web user
//        into clicking on something different from what the user perceives they are clicking on
        httpSecurity.headers().frameOptions().disable();
    }

}
