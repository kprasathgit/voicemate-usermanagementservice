package com.voicemate.apigatewayservice.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
public class Config {

	@Bean
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
				.authorizeExchange(auth -> auth.anyExchange().permitAll() // Permit
																			// all
																			// requests
				)

				.httpBasic(org.springframework.security.config.Customizer.withDefaults());
		return http.build();

	}

	@Bean
	RouteLocator rateLimitingRoutes(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes().route("UserManagementService",
				r -> r.path("/usermanagement/**").uri("http://localhost:8085/usermanagement")).build();

	}

	@Bean
	RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1000, 2000); // Adjusted for high traffic
	}

	@Bean
	GlobalFilter customGlobalFilter() {

		return (exchange, chain) -> {
			exchange.getRequest().getHeaders().forEach((name, values) -> {

				values.forEach(value -> System.out.println(name + "=" + value));
			});
			return chain.filter(exchange);
		};

	}

}
