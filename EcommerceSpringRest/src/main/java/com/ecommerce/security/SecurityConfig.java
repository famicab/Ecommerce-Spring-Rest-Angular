package com.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ecommerce.security.jwt.JwtAuthorizationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(name = "App Bearer Token", description = "Add only the JWT String, without headers", in=SecuritySchemeIn.HEADER ,type = SecuritySchemeType.HTTP, scheme = "Bearer", bearerFormat = "JWT")
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	private final AccessDeniedHandler accessDeniedHandler;
	private final JwtAuthorizationFilter authorizationFilter;
	
	private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
			new AntPathRequestMatcher("/h2-console/**"),
			new AntPathRequestMatcher("/swagger-ui/**"),
			new AntPathRequestMatcher("/v3/**")
	};
  
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
				
				authorizationManagerRequestMatcherRegistry
					.requestMatchers("/admin/**").hasAnyRole("ADMIN")
					.requestMatchers("/order/**", "/user/edit**").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/user/**", "/category/**", "/login/**", "/product/**", "/auth/**").permitAll()
					.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(httpSecuritySessionManagementConfigurer -> 
				httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.exceptionHandling((exception) -> {
			exception.accessDeniedHandler(accessDeniedHandler);
			exception.authenticationEntryPoint(authenticationEntryPoint);
		});
		http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	    return web -> web.ignoring()
	            .requestMatchers(WHITE_LIST_URLS);
	}
}
