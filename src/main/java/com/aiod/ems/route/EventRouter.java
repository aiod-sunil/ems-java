package com.aiod.ems.route;

import com.aiod.ems.handler.EventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.awt.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EventRouter {



	@Bean
	public RouterFunction<ServerResponse> router(EventHandler eventHandler){
		return nest(RequestPredicates.path("/ems/api").and(accept(MediaType.APPLICATION_JSON)),
				route(GET("/events"),eventHandler::getAllEvents));
	}

}
