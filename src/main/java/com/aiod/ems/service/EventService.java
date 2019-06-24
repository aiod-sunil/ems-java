package com.aiod.ems.service;

import com.aiod.ems.model.Event;
import com.aiod.ems.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EventService {

	private EventRepository repository;

	public EventService(EventRepository repository) {
		this.repository = repository;

	}

	public Flux<Event> all() {
		return this.repository.findAll();
	}

	public Mono<Event> get(String id) {
		return this.repository.findById(id);
	}

	public Mono<Event> update(String id, Event event) {
		return this.repository
				.findById(id)
				.map(p -> new Event(p.getId(), event.getName(), event.getType(), event.getStatus(), event.getCreatedDate()))
				.flatMap(this.repository::save);
	}

	public Mono<Event> delete(String id) {
		return this.repository
				.findById(id)
				.flatMap(p -> this.repository.deleteById(p.getId()).thenReturn(p));
	}

	public Mono<Event> create(Event email) {
		return this.repository
				.save(email);
	}
}

