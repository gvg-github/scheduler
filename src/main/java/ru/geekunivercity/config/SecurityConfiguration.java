package ru.geekunivercity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.geekunivercity.service.appuser.AppUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan("ru.geekunivercity.*")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailsServiceImpl userDetailsService;

//	TODO make it work!!! (encoder)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
						.authorizeRequests()
						.antMatchers("/").permitAll()
						.antMatchers("/login").permitAll()
						.antMatchers("/register").permitAll()
						.antMatchers("/confirm").permitAll()
						.anyRequest()
						.authenticated().and().csrf().disable()
						.formLogin()
						.loginPage("/login")
						.failureUrl("/login?error=true")
						.defaultSuccessUrl("/task/task-list")
						.usernameParameter("email")
						.passwordParameter("password")
						.and().logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/welcome").and().exceptionHandling();
	}

//TODO (encoder)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());//.passwordEncoder(passwordEncoder())
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/*.css");
		web.ignoring().antMatchers("/*.js");
	}

}
