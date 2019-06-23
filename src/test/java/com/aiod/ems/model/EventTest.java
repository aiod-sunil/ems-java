package com.aiod.ems.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("Event model Test Results")
class EventTest {

	private Event walking = new Event("101", "walking", "workout", "open", new Date());
	private Event runing = new Event("102", "running", "workout", "open", new Date());

	@Test
	@DisplayName("Event should be created")
	void eventShouldCreated() {

		assertNotNull(walking);
		assertNotSame(new Event("103", "running", "workout", "open", new Date()), walking);
		assertTrue(walking.getId().contentEquals("101"), () -> "walking event id should be equals");
		assertTrue(walking.getName().equals("running"), () -> "name and status are valid");

	}

	@Test
	@DisplayName("Flux validated with events")
	void fluxShouldExWith() {

		Flux<Event> events = Flux.just(walking, runing);

		StepVerifier.create(events)
				.expectNext(walking)
				.expectNext(runing)
				.expectNextCount(2)
				.expectComplete();
	}

}