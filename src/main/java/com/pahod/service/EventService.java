package com.pahod.service;

import com.pahod.model.Event;
import com.pahod.repository.EventDAO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class EventService {

    @Autowired
    EventDAO repository;

    public Event getEvent(Integer eventId) {
        return repository.getEvent(eventId);
    }

    public boolean checkAvailableSeats(Integer eventId, Integer requiredSeats) {
        return getEvent(eventId) != null && getEvent(eventId).getAvailableSeats() >= requiredSeats;
    }

    public List<Event> getAllEvents() {
        return repository.getAllEvents();
    }

    public Event addEvent(Event event) {
        return repository.saveEvent(event);
    }

    public void soldTicketsForEvent(Integer eventId, int soldSeats) {
        Event event = getEvent(eventId);
        event.setAvailableSeats(event.getAvailableSeats() - soldSeats);
    }
}
