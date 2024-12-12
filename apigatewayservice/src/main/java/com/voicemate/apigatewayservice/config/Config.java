package com.voicemate.apigatewayservice.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration // Marks this class as a configuration class, allowing Spring to process it.
@EnableWebFlux // Enables WebFlux support (needed for non-blocking reactive routes).
public class Config {

	/**
	 * This bean configures security settings for the API Gateway. It disables CSRF
	 * protection for simplicity (although this is not recommended for production).
	 * It also permits all requests to pass through (no security restrictions).
	 * Basic HTTP authentication is enabled but has default settings (not enforced
	 * in this example).
	 *
	 * @param http The http security object to configure.
	 * @return The configured security filter chain.
	 */
	@Bean
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {

		// Disable CSRF (Cross-Site Request Forgery) protection (not recommended for
		// production)
		http.csrf(csrf -> csrf.disable())
				// Permit all incoming requests (no security checks on the routes)
				.authorizeExchange(auth -> auth.anyExchange().permitAll())
				// Enable basic HTTP authentication (with default settings)
				.httpBasic(org.springframework.security.config.Customizer.withDefaults());
		return http.build(); // Return the built security configuration.

	}

	/**
	 * This bean defines the routes that the API Gateway will handle. Specifically,
	 * it maps a route to the UserManagementService, which handles requests with
	 * paths that start with "/usermanagement/**". The requests are forwarded to the
	 * URI "http://localhost:8085/usermanagement".
	 *
	 * @param routeLocatorBuilder The builder to define the routes.
	 * @return The configured RouteLocator that includes the defined route.
	 */
	@Bean
	RouteLocator rateLimitingRoutes(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes().route("UserManagementService",
				r -> r.path("/usermanagement/**").uri("http://localhost:8085/usermanagement")).build();

	}

	/**
	 * This bean configures a RedisRateLimiter that controls the rate of requests.
	 * In this example, it limits requests to 1000 per second, with a burst capacity
	 * of 2000. This ensures that the service can handle high traffic but also
	 * avoids overloading the back end.
	 *
	 * @return A RedisRateLimiter with specified rate limiting settings.
	 */
	@Bean
	RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1000, 2000); // Adjusted for high traffic
	}

	/**
	 * The GlobalFilter is a filter that is applied to all incoming requests passing
	 * through the Gateway. This filter intercepts each request, logs the request
	 * headers, and then forwards the request to the next handler in the processing
	 * chain.
	 * 
	 * In this implementation, it logs each request's headers, printing them to the
	 * console. The filter is a useful tool for debugging and monitoring the
	 * requests coming through the Gateway.
	 * 
	 * After logging, the filter allows the request to proceed by calling
	 * chain.filter(exchange).
	 *
	 * @return The configured GlobalFilter that intercepts all incoming requests.
	 */
	@Bean
	GlobalFilter customGlobalFilter() {
		// Log all request headers for debugging/monitoring
		return (exchange, chain) -> {
			exchange.getRequest().getHeaders().forEach((name, values) -> {

				values.forEach(value -> System.out.println(name + "=" + value));
			});

			// Proceed with the request and capture the response
			return chain.filter(exchange).doOnTerminate(() -> {
				// Wrap response to log the body
				exchange.getResponse().beforeCommit(() -> {
					DataBuffer buffer = exchange.getResponse().bufferFactory().wrap("Response Body".getBytes());
					exchange.getResponse().writeWith(Flux.just(buffer)).subscribe();
					return Mono.empty();
				});
				// Log the response status code after the response is processed
				System.out.println("Response Status Code: " + exchange.getResponse().getStatusCode());
			});

//			// Continue with the request processing chain
//			return chain.filter(exchange);
		};

	}
	// NOTE:
	/*
	 * The GlobalFilter is a filter that gets applied to all incoming requests,
	 * regardless of the route, so every request that passes through the Gateway
	 * will be processed by the code inside this filter.
	 * 
	 * Here's how it works:
	 * 
	 * The filter intercepts every incoming request. It logs the URI of the request
	 * (exchange.getRequest().getURI()). It also logs the request headers, iterating
	 * over each header and printing them. After logging the request, it proceeds to
	 * the next filter or handler in the chain using chain.filter(exchange).
	 */

}
