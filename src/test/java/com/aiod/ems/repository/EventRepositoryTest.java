package com.aiod.ems.repository;

import com.aiod.ems.model.Event;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Date;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class EventRepositoryTest {

	@Autowired
	private EventRepository eventRepository;

	private Event walking = new Event("101", "walking", "workout", "open", new Date());
	private Event running = new Event("102", "running", "workout", "open", new Date());
	private Event gym = new Event("103", "gym", "workout", "open", new Date());


	@Test
	void getMyFavorites() {

		Publisher<Event> setup =
				this.eventRepository.deleteAll().thenMany(this.eventRepository.saveAll(Flux.just(this.gym, this.running)));

		Publisher<Event> find = this.eventRepository.getMyFavorites();

		Publisher<Event> composite = Flux.from(setup).thenMany(find);

		StepVerifier.create(composite).expectNext(this.gym, this.running).verifyComplete();

	}

	@Test
	void findByName() {
		Publisher<Event> setup = this.eventRepository
				.deleteAll()
				.thenMany(this.eventRepository.saveAll(Flux.just(walking, running, gym)));

		Publisher<Event> find = this.eventRepository.findByName("walking");
		Publisher<Event> composite=Flux.from(setup).thenMany(find);
		StepVerifier.create(composite).expectNext(walking).expectComplete();

	}

	@Test
	public void testFindAllWithVirtualTime() {
		Supplier<Flux<Event>> setupSupplier =
				() ->
						this.eventRepository
								.deleteAll()
								.thenMany(this.eventRepository.saveAll(Flux.just(this.walking, this.running)))
								.thenMany(eventRepository.findAll())
								.delayElements(Duration.ofSeconds(5));

		StepVerifier.withVirtualTime(setupSupplier)
				.expectSubscription()
				.expectNoEvent(Duration.ofSeconds(5))
				.expectNextMatches(event -> event.getName().equalsIgnoreCase("walking"))
				.thenAwait(Duration.ofSeconds(10)) // t = 10
				.expectNextMatches(team -> team.getName().equalsIgnoreCase("running"))
				.expectComplete()
				.verify(Duration.ofSeconds(5));
	}
}