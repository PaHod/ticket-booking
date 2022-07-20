package com.pahod.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EventTest {

    @Test
    void updateFrom_all_field_updated() {
        //given
        long id = 15;
        Event eventTemplate = Event.builder().id(id).title("Concert").location("Cnocert Hall").availableSeats(1000).build();
        Event eventToUpdate = new Event();
        eventToUpdate.setId(id);

        //when
        eventToUpdate.updateFrom(eventTemplate);

        //then
        assertEquals(eventToUpdate, eventTemplate);
    }

    @Test
    void updateFrom_all_id_not_updated() {
        //given
        Event eventTemplate = Event.builder().id(15).title("Concert").location("Cnocert Hall").availableSeats(1000).build();
        Event eventToUpdate = new Event();

        //when
        eventToUpdate.updateFrom(eventTemplate);

        //then
        assertNotEquals(eventToUpdate.getId(), eventTemplate.getId());
    }
}