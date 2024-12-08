package com.voicemate.usermanagementservice.common;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;

/**
 * Utility class for generating and validating JSON Web Tokens (JWT).
 */
public class JwtUtils {

	// Inject the algorithm from application.properties
	@Value("${jwt.algorithm:HmacSHA512}") // Default to HmacSHA512 if not set
	private static String algorithm;

	// Key size for the secret key
	@Value("${jwt.key-size:512}") // Default key size is 512 bits
	private static int keySize;

	private static final SecretKey SECRET_KEY = generateSecretKey(algorithm, keySize);

	/**
	 * Dynamically generates a SecretKey based on the given algorithm and key size.
	 *
	 * @param algorithm the algorithm used for key generation
	 * @param keySize   the size of the key in bits
	 * @return a generated SecretKey
	 */
	private static SecretKey generateSecretKey(String algorithm, int keySize) {

		try {

			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(keySize);
			return keyGenerator.generateKey();

		} catch (Exception e) {
			throw new RuntimeException("Error Generating Key", e);
		}

	}

	/**
	 * Generates a JWT token for the given username.
	 *
	 * @param username the username to be included as the subject of the token
	 * @return the generated JWT token
	 */
	public String generateToken(String userName) {

		long currentTimeMillis = System.currentTimeMillis();
		return Jwts.builder().claim("sub", userName) // Subject claim
				.claim("iat", new java.util.Date(currentTimeMillis))// Issued-at timestamp claim
				.claim("exp", new java.util.Date(currentTimeMillis + 86400000)) // Expiration claim (1 day)
				.signWith(SECRET_KEY) // Use the dynamically generated secret key for signing
				.compact();

	}

	/**
	 * Parses the JWT token and extracts the username (subject).
	 *
	 * @param token the JWT token to parse
	 * @return the username extracted from the token
	 * @throws IllegalArgumentException if the token is invalid
	 */
}
