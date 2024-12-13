package com.voicemate.translationservice.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RateLimiter {

	private static final int DEFAULT_RATE_LIMIT = 100000;// Maximum requests per user per minute
	private static final int TIME_WINDOW = 60; // in seconds (1 minute)

	private final StringRedisTemplate redisTemplate;

	public RateLimiter(StringRedisTemplate stringRedisTemplate) {
		this.redisTemplate = stringRedisTemplate;
	}

	public boolean isAllowed(String userId) {

		String key = "rate_limit:" + userId; // Redis key to track user requests

		ValueOperations<String, String> ops = redisTemplate.opsForValue();

		// Get the dynamic rate limit based on current system load or time of day
		int rateLimit = getDynamicRateLimit();

		// Increment the request count in Redis
		Long count = ops.increment(key, 1);

		// If count is 1, set an expiry time for the key (TTL)
		if (count == 1) {
			redisTemplate.expire(key, TIME_WINDOW, TimeUnit.SECONDS);
		}

		// If count exceeds the limit, deny the request
		return count <= rateLimit;

	}

	private int getDynamicRateLimit() {
		// Placeholder for dynamic rate limiting logic
		int rateLimit = DEFAULT_RATE_LIMIT;

		// Example 1: Reduce limit during peak traffic times (e.g., 9 AM - 6 PM)
		int currentHour = java.time.LocalTime.now().getHour();
		if (currentHour >= 9 && currentHour <= 18) {
			rateLimit = 50000; // Lower the rate limit during peak hours
		}
		// Example 2: Adjust based on system load (this is a mock example)
		if (isSystemUnderHeavyLoad()) {
			rateLimit = 20000; // Lower the rate limit when the system is under heavy load
		}

		return rateLimit;
	}

	// Placeholder method for determining system load (example logic)
	private boolean isSystemUnderHeavyLoad() {
		// This is just an example. In a real system, you'd fetch actual server metrics.
		int cpuLoad = getSystemCpuLoad(); // Example method to get CPU load
		return cpuLoad > 80; // If CPU load exceeds 80%, we consider the system under heavy load
	}

	// Mock method to simulate getting system CPU load
	private int getSystemCpuLoad() {
		// Replace with actual logic to get CPU load from the server/system
		return 85; // For demonstration, assuming CPU load is high
	}
}
