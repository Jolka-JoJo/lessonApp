package lt.ku.LessonApp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lt.ku.LessonApp.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		
		auth
			.userDetailsService(this.userService)
			.passwordEncoder(bc);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()

				.antMatchers("/login/**").permitAll()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/logout/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/lesson/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/task/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/task/files/{id}").hasAnyRole("USER", "ADMIN")
				.antMatchers("/**").hasRole("ADMIN")
				.anyRequest().authenticated()
		.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.failureForwardUrl("/login-error")
		.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
