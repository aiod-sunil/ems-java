package com.aiod.ems.handler;

import com.aiod.ems.model.Event;
import com.aiod.ems.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class EventHandler {

 private EventService eventService;

	public EventHandler(EventService eventService) {
		this.eventService = eventService;
	}

	public Mono<ServerResponse> getAllEvents(ServerRequest request){
		Flux<Event> eventFlux = eventService.getAllEvents();
		return ok().contentType(APPLICATION_JSON).body(eventFlux, Event.class);

	}
}
