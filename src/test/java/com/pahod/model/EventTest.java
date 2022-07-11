package com.pahod.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EventTest {

    @Test
    void updateFrom_all_field_updated() {
        //given
        Integer id = 15;
        Event eventTemplate = new Event(id, "Concert", "Cnocert Hall", 1000);
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
        Event eventTemplate = new Event(15, "Concert", "Cnocert Hall", 1000);
        Event eventToUpdate = new Event();

        //when
        eventToUpdate.updateFrom(eventTemplate);

        //then
        assertNotEquals(eventToUpdate.getId(), eventTemplate.getId());
    }
}