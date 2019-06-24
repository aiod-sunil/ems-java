package com.aiod.ems.route;

import com.aiod.ems.handler.EventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EventRouter {


	@Bean
	public RouterFunction<ServerResponse> router(EventHandler eventHandler) {
		return nest(RequestPredicates.path("/ems/api").and(accept(APPLICATION_JSON)),
				route(GET("/events"), eventHandler::all)
				.andRoute(GET("/events/{id}"), eventHandler::getById)
				.andRoute(DELETE("events/{id}"), eventHandler::deleteById)
				.andRoute(POST("/events"), eventHandler::create)
				.andRoute(PUT("/events/{id}"), eventHandler::updateById));
	}

}
