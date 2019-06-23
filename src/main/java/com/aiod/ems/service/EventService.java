package com.aiod.ems.service;

import com.aiod.ems.model.Event;
import com.aiod.ems.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class EventService {

	private EventRepository repository;

	public EventService(EventRepository repository){
		this.repository=repository;

	}

	public Flux<Event> getAllEvents(){
		return  repository.findAll();

	}
}
