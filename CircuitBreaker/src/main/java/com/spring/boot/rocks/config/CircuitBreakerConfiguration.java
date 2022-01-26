package com.spring.boot.rocks.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Component
public class CircuitBreakerConfiguration {

	@Bean
	public CircuitBreaker countCircuitBreaker() {
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
				.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED).slidingWindowSize(10)
				.slowCallRateThreshold(65.0f).slowCallDurationThreshold(Duration.ofSeconds(3)).build();
		CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
		CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("AppUserSearchServiceBasedOnTime");
		return cb;
	}

	@Bean
	public CircuitBreaker timeCircuitBreaker() {
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
				.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED).minimumNumberOfCalls(3)
				.slidingWindowSize(10).failureRateThreshold(70.0f).waitDurationInOpenState(Duration.ofSeconds(10))
				.build();
		CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
		CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("AppUsersSearchServiceBasedOnCount");
		return cb;
	}
}
