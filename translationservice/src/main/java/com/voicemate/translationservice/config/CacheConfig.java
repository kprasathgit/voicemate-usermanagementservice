package com.voicemate.translationservice.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		/*
		 * .entryTtl(Duration.ofMinutes(10)) // Optional: Set TTL for cache entries
		 * .disableCachingNullValues(); // Optional: Disable caching of null values
		 */
		return RedisCacheManager.builder(redisCacheWriter).cacheDefaults(redisCacheConfiguration).build();

	}
}
