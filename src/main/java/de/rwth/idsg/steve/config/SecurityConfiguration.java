package de.rwth.idsg.steve.config;

import de.rwth.idsg.steve.SteveProdCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static de.rwth.idsg.steve.SteveConfiguration.CONFIG;

/**
 * @author Sevket Goekay <goekay@dbis.rwth-aachen.de>
 * @since 07.01.2015
 */
@Configuration
@EnableWebSecurity
@Conditional(SteveProdCondition.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(CONFIG.getAuth().getUserName())
            .password(CONFIG.getAuth().getPassword())
            .roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/static/**")
           .antMatchers("/views/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String prefix = "/manager/";
        http
            .authorizeRequests()
                .antMatchers(prefix + "**").hasRole("ADMIN")
                .and()
            .sessionManagement()
                .invalidSessionUrl(prefix + "signin")
                .and()
            .formLogin()
                .loginPage(prefix + "signin")
                .permitAll()
                .and()
            .logout()
                .logoutUrl(prefix + "signout")
                .and()
            .httpBasic();
    }

}
