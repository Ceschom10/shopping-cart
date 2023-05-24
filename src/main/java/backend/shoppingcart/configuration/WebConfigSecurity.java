package backend.shoppingcart.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
	@Autowired
    private Environment env;
	
	 private static final String[] SWAGGER_WHITELIST = {
	            "/v3/api-docs/**",
	            "/swagger-ui/**",
	            "/swagger-ui.html",
	            "/swagger/**"
	    };

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User
	    	.builder()
	    	.username(env.getProperty("spring.security.user.name"))
	        .password(passwordEncoder().encode(env.getProperty("spring.security.user.password")))
	        .roles("USER_ROLE")
	        .build();
	    return new InMemoryUserDetailsManager(user);
	  }
	  
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    	.and()
	    	.csrf().disable()
	    	.cors().and()
	    	.exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).and()
	    	.authorizeHttpRequests((authz) -> authz
	    			.antMatchers(SWAGGER_WHITELIST).permitAll()
	    			.antMatchers("/check/**").hasRole("USER_ROLE")
	    			.antMatchers("/order/**").hasRole("USER_ROLE")
	    			.anyRequest().authenticated()
	    			)
	        .httpBasic(Customizer.withDefaults());
	    return http.build();
	  }
	  
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
}
