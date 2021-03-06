package com.pahod.service;

import com.pahod.model.Event;
import com.pahod.repository.EventDAO;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    EventDAO repository;

    public Event addEvent(Event event) {
        return repository.addEvent(event);
    }

    public Event getEvent(long eventId) {
        return repository.getEvent(eventId);
    }

    public List<Event> getAllEvents() {
        return repository.getAllEvents();
    }

    public void soldTicketsForEvent(long eventId, int soldSeats) {
        Event event = getEvent(eventId);
        event.setAvailableSeats(event.getAvailableSeats() - soldSeats);
    }

    public boolean checkAvailableSeats(long eventId, int requiredSeats) {
        logger.trace("check if there available seats for eventId: {}", eventId);
        return getEvent(eventId) != null && getEvent(eventId).getAvailableSeats() >= requiredSeats;
    }
}
