
package com.voicemate.usermanagementservice.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * Class for generating and validating JSON Web Tokens (JWT).
 */
@Service
public class JwtService {

//	// Inject the algorithm from application.properties
//	@Value("${jwt.algorithm:HmacSHA512}") // Default to HmacSHA512 if not set
//	private String algorithm;
//
//	// Key size for the secret key
//	@Value("${jwt.key-size:512}") // Default key size is 512 bits
//	private int keySize;

	private String secretKey;

	public JwtService() {

		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
			keyGenerator.init(512);
			SecretKey key = keyGenerator.generateKey();
			secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

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

	private Key getKey() {

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Parses the JWT token and extracts the username (subject).
	 *
	 * @param token the JWT token to parse
	 * @return the username extracted from the token
	 * @throws IllegalArgumentException if the token is invalid
	 */

}
