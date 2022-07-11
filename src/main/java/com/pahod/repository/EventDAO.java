package com.pahod.repository;

import com.pahod.model.Event;
import com.pahod.repository.storage.CommonInMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventDAO implements IEventDAO {

    @Autowired
    CommonInMemoryStorage storage;

    public Event saveEvent(Event eventToSave) {
        Event savedEvent = storage.getEvents().get(eventToSave.getId());

        if (eventToSave.getId() == null || savedEvent == null) {
            savedEvent = new Event();
            savedEvent.setId(storage.generateNextIdForClass(Event.class));
            storage.getEvents().put(savedEvent.getId(), savedEvent);
        }
        savedEvent.updateFrom(eventToSave);
        return savedEvent;
    }

    public Event getEvent(Integer eventId) {
        return storage.getEvents().get(eventId);
    }

    @Override
    public List<Event> getAllEvents() {
        return new ArrayList<>(storage.getEvents().values());
    }
}
