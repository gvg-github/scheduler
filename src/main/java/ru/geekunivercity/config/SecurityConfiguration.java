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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.geekunivercity.service.appuser.AppUserDetailsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@ComponentScan("ru.geekunivercity.*")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailsServiceImpl userDetailsService;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

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
						.antMatchers("/forgot-password").permitAll()
						.antMatchers("/reset-password").permitAll()
						.antMatchers("/css/**").permitAll()
						.anyRequest()
						.authenticated().and().csrf().disable()
						.formLogin()
						.loginPage("/login")
						.failureUrl("/login?error=true")
						.usernameParameter("email")
						.passwordParameter("password")
						.successHandler(new AuthenticationSuccessHandler() {
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
																									Authentication authentication) throws IOException, ServletException {
								redirectStrategy.sendRedirect(request, response, "/task/task-list");
							}
						})
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
