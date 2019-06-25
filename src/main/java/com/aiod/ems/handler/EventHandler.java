package com.aiod.ems.handler;

import com.aiod.ems.model.Event;
import com.aiod.ems.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class EventHandler {

	private EventService eventService;

	public EventHandler(EventService eventService) {
		this.eventService = eventService;
	}

	public Mono<ServerResponse> getById(ServerRequest r) {
		return defaultReadResponse(this.eventService.get(id(r)));
	}

	public 	Mono<ServerResponse> all(ServerRequest r) {
		return defaultReadResponse(this.eventService.all());
	}

	public Mono<ServerResponse> deleteById(ServerRequest r) {
		return defaultReadResponse(this.eventService.delete(id(r)));
	}

	public Mono<ServerResponse> updateById(ServerRequest r) {
		Flux<Event> id = r.bodyToFlux(Event.class)
				.flatMap(p -> this.eventService.update(id(r), p));
		return defaultReadResponse(id);
	}

	public 	Mono<ServerResponse> create(ServerRequest request) {
		Flux<Event> flux = request
				.bodyToFlux(Event.class)
				.flatMap(toWrite -> this.eventService.create(toWrite));
		return defaultWriteResponse(flux);
	}

	private static Mono<ServerResponse> defaultWriteResponse(Flux<Event> eventPublisher) {
		return Mono
				.from(eventPublisher)
				.flatMap(event -> created(URI.create("/event/" + event.getId()))
						.contentType(APPLICATION_JSON)
						.build()
				);
	}


	private static Mono<ServerResponse> defaultReadResponse(Publisher<Event> eventPublisher) {
		return ok().contentType(APPLICATION_JSON).body(eventPublisher, Event.class);
	}

	private static String id(ServerRequest r) {
		return r.pathVariable("id");
	}
}
