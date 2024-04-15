package com.ecommerce.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.model.user.User;
import com.ecommerce.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter{
	
	private final JwtTokenProvider tokenProvider;
	private final CustomUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			String token = getJwtFromRequest(request);
			
			if(StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
				Long userId = tokenProvider.getUserIdFromJWT(token);
				
				User user = (User)userDetailsService.loadUserById(userId);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getRoles(), user.getAuthorities());
				
				authentication.setDetails(new WebAuthenticationDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
		} catch (Exception e) {
			log.info("Couldn't handle user authentication");
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(JwtTokenProvider.TOKEN_HEADER);
		
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
			return bearerToken.substring(JwtTokenProvider.TOKEN_PREFIX.length(), bearerToken.length());
		}
		
		return null;
	}

}
