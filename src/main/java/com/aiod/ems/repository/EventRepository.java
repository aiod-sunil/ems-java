package com.aiod.ems.repository;

import com.aiod.ems.model.Event;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository extends ReactiveMongoRepository<Event, String> {
	@Query("{name: {$in: ['running', 'gym']} }")
	Flux<Event> getMyFavorites();

	Mono<Event> findByName(String name);
}
