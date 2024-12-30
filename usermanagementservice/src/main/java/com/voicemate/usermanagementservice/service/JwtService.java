
package com.voicemate.usermanagementservice.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Class for generating and validating JSON Web Tokens (JWT).
 */
@Service
public class JwtService {

	@Value("${jwt.secret-key}")
	private String secretKey;
//jwt.secret-key=uEC8GLJCJ4gDmT7AljPiUdf/bvxhdpCegLiRNfdSYy2FmE1zQqolvaqgxbV7oogY2A+qv2FfilYddocOajoniw==

//	private String secretKey;
//
//	public JwtService() {
//
//		try {
//			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
//			keyGenerator.init(512);
//			SecretKey key = keyGenerator.generateKey();
//			secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
//		} catch (NoSuchAlgorithmException e) {
//			throw new RuntimeException(e);
//		}
//	}

	/**
	 * Dynamically generates a SecretKey based on the given algorithm and key size.
	 *
	 * @param algorithm the algorithm used for key generation
	 * @param keySize   the size of the key in bits
	 * @return a generated SecretKey
	 */
//	private SecretKey generateSecretKey(String algorithm, int keySize) {
//
//		try {
//
//			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
//			keyGenerator.init(keySize);
//			return keyGenerator.generateKey();
//
//		} catch (Exception e) {
//			throw new RuntimeException("Error Generating Key", e);
//		}
//
//	}

	/**
	 * Generates a JWT token for the given username.
	 *
	 * @param username the username to be included as the subject of the token
	 * @return the generated JWT token
	 */
	public String generateToken(String userName) {

		Map<String, Object> claims = new HashMap<String, Object>();
		long currentTimeMillis = System.currentTimeMillis();

		return Jwts.builder().claims().add(claims).subject(userName).issuedAt(new Date(currentTimeMillis))
				.expiration(new Date(currentTimeMillis + 86400000)).and().signWith(getKey()).compact();

//		return Jwts.builder().claim("sub", userName) // Subject claim
//				.claim("iat", new java.util.Date(currentTimeMillis))// Issued-at timestamp claim
//				.claim("exp", new java.util.Date(currentTimeMillis + 86400000)) // Expiration claim (1 day)
//				.signWith(SECRET_KEY) // Use the dynamically generated secret key for signing
//				.compact();

	}

	private SecretKey getKey() {

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Extracts the username from a given JWT token.
	 *
	 * @param token the JWT token
	 * @return the username extracted from the token
	 */
	public String extractUserNameFromToken(String token) {
		// extract the username from jwt token
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
	}

	/**
	 * Validates the given JWT token.
	 *
	 * @param token       the JWT token
	 * @param userDetails the user details
	 * @return true if the token is valid, false otherwise
	 */
	public boolean validateToken(String token, UserDetails userDetails) {

		final String userName = extractUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());

	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
