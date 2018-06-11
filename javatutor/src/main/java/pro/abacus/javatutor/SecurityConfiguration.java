package pro.abacus.javatutor;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import org.springframework.web.filter.CorsFilter;

import pro.abacus.javatutor.controller.RegistrationController;
import pro.abacus.javatutor.security.AuthSuccessHandler;
import pro.abacus.javatutor.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	final static Logger log = LoggerFactory.getLogger( SecurityConfiguration.class);
	

	private UserDetailsServiceImpl userDetailsService;
	
	private CorsFilter corsFilter;

	@Autowired
	public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Bean
	public AuthSuccessHandler authSuccessHandler() {
		return new AuthSuccessHandler();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**");
            
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	        http.csrf().disable();
	       http.cors();
	        http.headers().frameOptions().disable();
	      http 
            .formLogin()
            .loginProcessingUrl("/api/signin")
            .successHandler(authSuccessHandler())
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()
            .and()
            .rememberMe()
	      .and()
	        .headers()
	        .frameOptions()
	        .disable()
          .and()
            .authorizeRequests()
            .antMatchers("/api/signin").permitAll()
	        .antMatchers("/api/signup").permitAll()
            .antMatchers("/api/javaquestions/**")  
            .authenticated()
            .anyRequest()
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessUrl("/api/signin").permitAll()
            .permitAll();
            
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	

}
