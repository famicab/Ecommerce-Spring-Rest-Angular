package com.ecommerce.security.jwt;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ecommerce.model.user.User;
import com.ecommerce.model.user.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.java.Log;

@Component
@Log
public class JwtTokenProvider {

	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String TOKEN_TYPE = "JWT";
	
	@Value("${jwt.secret:thisIsASecretThatIsCompletelySafeToStoreInHereSoThereIsNoProblemAtAll}")
	private String jwtSecret;
	
	@Value("${jwt.token-expiration:864000}")
	private int tokenDurationInSeconds;
	
	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		Date tokenExpirationDate = new Date(System.currentTimeMillis() + (tokenDurationInSeconds * 1000));
		
		return Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
				.header().add("typ", TOKEN_TYPE)
				.and()
				.subject(Long.toString(user.getId()))
				.issuedAt(new Date())
				.expiration(tokenExpirationDate)
				.claim("fullname", user.getFullName())
				.claim("roles", user.getRoles().stream().map(UserRole::name).collect(Collectors.joining(", ")))
				.compact();				
	}
	
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
						.verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
						.build()
						.parseSignedClaims(token)
						.getPayload();
		
		return Long.parseLong(claims.getSubject());
	}
	
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseSignedClaims(authToken);
			return true;
		} catch(SignatureException e) {
			log.info("Error in the token's signature: " + e.getMessage());
		} catch(MalformedJwtException e) {
			log.info("Malformed token: " + e.getMessage());
		} catch(ExpiredJwtException e) {
			log.info("The token expired: " + e.getMessage());
		} catch(UnsupportedJwtException e) {
			log.info("Token JWT not supported: " + e.getMessage());
		} catch(IllegalArgumentException e) {
			log.info("JWT Claims are empty: " + e.getMessage());
		}
		return false;
	}
}
