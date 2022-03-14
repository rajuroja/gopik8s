/**
 * 
 */
package com.intech.user;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Akash Budhwani
 *
 */
@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/api/users/**").uri("lb://USER-SERVICE"))
				.route(p -> p.path("/api/movies/**").uri("lb://MOVIE-SERVICE"))
				.route(p -> p.path("/api/ratings/**").uri("lb://RATINGS-SERVICE"))
				.build();
	}

}
