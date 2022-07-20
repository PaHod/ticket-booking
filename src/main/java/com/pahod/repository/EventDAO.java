package com.pahod.repository;

import com.pahod.model.Event;
import com.pahod.repository.storage.CommonInMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventDAO {

    @Autowired
    CommonInMemoryStorage storage;

    public Event addEvent(Event eventToSave) {
        Event savedEvent = new Event();
        savedEvent.setId(storage.generateNextIdForClass(Event.class));
        storage.getEvents().put(savedEvent.getId(), savedEvent);
        savedEvent.updateFrom(eventToSave);
        return savedEvent;
    }

    public Event saveEvent(Event eventToSave) {
        Event savedEvent = storage.getEvents().get(eventToSave.getId());

        if (savedEvent != null) {
            savedEvent.updateFrom(eventToSave);
        }
        return savedEvent;
    }

    public Event getEvent(Long eventId) {
        return storage.getEvents().get(eventId);
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(storage.getEvents().values());
    }
}
